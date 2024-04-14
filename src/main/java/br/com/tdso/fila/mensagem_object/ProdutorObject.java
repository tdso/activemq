package br.com.tdso.fila.mensagem_object;

import br.com.tdso.model.Pedido;
import br.com.tdso.model.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class ProdutorObject {

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

        for (int i = 0; i < 10; i++){
            Pedido pedido = new PedidoFactory().geraPedido(i);
            Message message = session.createObjectMessage(pedido);
            producer.send(message);
        }
        session.close();
        connection.close();
        context.close();
    }

}
