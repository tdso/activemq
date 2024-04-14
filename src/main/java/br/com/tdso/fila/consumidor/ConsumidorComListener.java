package br.com.tdso.fila.consumidor;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class ConsumidorComListener {

    public static void main (String[] args){

        final boolean HABILITA_TRANSACAO = false;
        final String ALIAS_FILA = "financeiro";

        Session session = null;
        ConnectionFactory factory = null;
        Connection connection = null;

        try {
            InitialContext context = new InitialContext();
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(HABILITA_TRANSACAO, Session.AUTO_ACKNOWLEDGE);
            Destination fila = (Destination) context.lookup(ALIAS_FILA);
            MessageConsumer consumer = session.createConsumer(fila);

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
