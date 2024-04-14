package br.com.tdso.fila.mensagem_object;

import br.com.tdso.model.Pedido;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class ConsumidorObject {

    public static void main (String[] args){

        final boolean HABILITA_TRANSACAO = false;
        final String ALIAS_FILA = "financeiro";

        //System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,java.util,br.com.tdso.model");
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

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
                    ObjectMessage objeto = (ObjectMessage) message;
                    try {
                        Pedido pedido = (Pedido)objeto.getObject();
                        System.out.println("Mensagem: " + pedido.getIdPedido() );
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
