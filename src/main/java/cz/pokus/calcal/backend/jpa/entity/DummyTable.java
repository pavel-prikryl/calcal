package cz.pokus.calcal.backend.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DUMMY_TABLE")
public class DummyTable implements Serializable {

    private static final long serialVersionUID = -428294616642927692L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "TEXTIK", nullable = true, length = 100)
    private String textik;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextik() {
        return textik;
    }

    public void setTextik(String textik) {
        this.textik = textik;
    }

    @Override
    public String toString() {
        return "DummyTable [id=" + id + ", textik=" + textik + "]";
    }

}
