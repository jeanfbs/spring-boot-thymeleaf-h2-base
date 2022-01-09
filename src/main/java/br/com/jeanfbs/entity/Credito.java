package br.com.jeanfbs.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credito")
    private Long id;
    private String data;
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_participante",
            referencedColumnName = "id_participante",
            foreignKey = @ForeignKey(name = "id_participante_pk")
    )
    private Participante participante;

    public Credito() {
        participante = new Participante();
    }

    public Credito(Double valor, String data, Participante participante) {
        this.data = data;
        this.valor = valor;
        this.participante = participante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credito credito = (Credito) o;
        return Objects.equals(id, credito.id) &&
                Objects.equals(data, credito.data) &&
                Objects.equals(valor, credito.valor) &&
                Objects.equals(participante, credito.participante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, valor, participante);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Credito{");
        sb.append("id=").append(id);
        sb.append(", data='").append(data).append('\'');
        sb.append(", valor=").append(valor);
        sb.append(", participante=").append(participante);
        sb.append('}');
        return sb.toString();
    }
}
