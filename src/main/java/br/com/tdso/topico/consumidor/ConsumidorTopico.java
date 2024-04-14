package br.com.tdso.topico.consumidor;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;
// consome msg apenas quando estiver online - se o produtor enviar msg e esse
// consumidor estiver offline nunca receberá msg pois não está identificado (não é assinanate durable)
public class ConsumidorTopico {

    public static void main (String[] args){

        final boolean HABILITA_TRANSACAO = false;
        final String ALIAS_TOPICO = "notafiscal";

        Session session = null;
        ConnectionFactory factory = null;
        Connection connection = null;

        try {
            InitialContext context = new InitialContext();
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(HABILITA_TRANSACAO, Session.AUTO_ACKNOWLEDGE);
            Topic topico = (Topic) context.lookup(ALIAS_TOPICO);
            MessageConsumer consumer = session.createConsumer(topico);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage texto = (TextMessage) message;
                    try {
                        System.out.println("Mensagem: " + texto.getText() );
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            System.out.println("Waiting ... Tecle algo .... " );
            new Scanner(System.in).nextLine();
            System.out.println("Fechando conexões ... end ... " );

            session.close();
            connection.close();
            context.close();
        } catch (NamingException | JMSException err){
            System.out.println("Erro: " + err.getMessage());
            System.out.println("Erro: " + err.getCause());
        }
    }
}
