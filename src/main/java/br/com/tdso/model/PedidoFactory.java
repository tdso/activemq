package br.com.tdso.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PedidoFactory {

    public Pedido geraPedido(Integer i){
        Pedido pedido = new Pedido();
        pedido.idPedido = i;
        pedido.dataPedido = geraData("10/02/2021");
        pedido.itens.add(geraItem(i));
        pedido.itens.add(geraItem(i + 1));
        return pedido;
    }

    private Item geraItem(int i) {
        Item item = new Item();
        item.idItem = i;
        item.nomeItem = "Item " + i;
        return item;
    }

    private Calendar geraData(String dataComString) {
        try {
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataComString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            return cal;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
