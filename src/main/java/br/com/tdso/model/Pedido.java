package br.com.tdso.model;

//import jakarta.xml.bind.annotation.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    Integer idPedido;
    Calendar dataPedido;

    @XmlElementWrapper(name="itens")
    @XmlElement(name="item")
    List<Item> itens = new ArrayList<>();

    public Pedido() {
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", dataPedido=" + dataPedido +
                ", itens=" + itens +
                '}';
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}
