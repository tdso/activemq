package br.com.tdso.fila.produtor;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Produtor {

    public static void main(String[] args) throws NamingException, JMSException {

        final boolean HABILITA_TRANSACAO = false;
        final String ALIAS_FILA = "financeiro";

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(HABILITA_TRANSACAO, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup(ALIAS_FILA);
        MessageProducer producer = session.createProducer(fila);

        for (int i = 0; i < 100; i++){
            TextMessage message = session.createTextMessage("<id>"+ (i + 1) +
                    "/<id> + <paylod>MENSAGEM DADOS TESTE</payload>");
            producer.send(message);
        }
        session.close();
        connection.close();
    }
}
