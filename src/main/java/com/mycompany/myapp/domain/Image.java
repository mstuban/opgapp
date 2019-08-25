package com.mycompany.myapp.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Marko Štuban (AG04) on 25/08/2019
 * @project opgapp
 */
@Entity
@Table(name = "image")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private byte[] byteArray;

    public Image() {
    }

    public Image(byte[] bytes) {
        this.byteArray = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
