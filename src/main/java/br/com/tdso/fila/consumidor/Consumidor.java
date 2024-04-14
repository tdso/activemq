package br.com.tdso.fila.consumidor;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class Consumidor {

    public static void main(String[] args) throws JMSException, NamingException {

        InitialContext context = new InitialContext();
        // o nome a ser buscado pelo lookup esta definido na documentacao
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        // processo
        //new Scanner(System.in).nextLine();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        Message msg = consumer.receive();

        System.out.println(msg);

        new Scanner(System.in).nextLine();

        // fim processo

        connection.close();
        context.close();
    }

}
