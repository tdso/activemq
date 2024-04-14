package br.com.tdso.topico.produtor;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProdutorTopico {

    public static void main(String[] args) throws NamingException, JMSException {

        final boolean HABILITA_TRANSACAO = false;
        final String ALIAS_TOPICO = "notafiscal";

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(HABILITA_TRANSACAO, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) context.lookup(ALIAS_TOPICO);
        //Destination fila = (Destination) context.lookup(ALIAS_TOPICO);
        MessageProducer producer = session.createProducer(topico);

        //for (int i = 0; i < 100; i++){
            TextMessage message = session.createTextMessage("<id>999Y"+
                    "/<id><paylod>MENSAGEM DADOS TESTE</payload>");
            producer.send(message);
        //}
        session.close();
        connection.close();
        context.close();
    }
}
