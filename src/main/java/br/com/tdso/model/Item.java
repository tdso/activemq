package br.com.tdso.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    Integer idItem;
    String nomeItem;
}
