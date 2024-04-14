package br.com.tdso.fila.mensagem_xml;

import br.com.tdso.model.Pedido;
import br.com.tdso.model.PedidoFactory;
//import jakarta.xml.bind.JAXB;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class ProdutorXml {
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
            String xml = gerarXML(i);
            TextMessage message = session.createTextMessage(xml);
            producer.send(message);
        }
        session.close();
        connection.close();
        context.close();
    }

    private static String gerarXML(Integer i) {
        Pedido pedido = new PedidoFactory().geraPedido(i);
        StringWriter pedidoXml = new StringWriter();
        JAXB.marshal(pedido, pedidoXml);
        return pedidoXml.toString();
    }
}
