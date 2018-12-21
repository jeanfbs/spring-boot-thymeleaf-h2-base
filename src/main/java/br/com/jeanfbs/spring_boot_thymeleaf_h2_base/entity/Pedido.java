package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    private Integer quantidade;

    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_participante",
            referencedColumnName = "id_participante",
            foreignKey = @ForeignKey(name = "id_pedido_participante_pk")
    )
    private Participante participante;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_produto",
            referencedColumnName = "id_produto",
            foreignKey = @ForeignKey(name = "id_pedido_produto_pk")
    )
    private Produto produto;


    public Pedido() {
    }


    public Pedido(Integer quantidade, Double valor, Participante participante, Produto produto) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.participante = participante;
        this.produto = produto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getTotaPedido(){
        return this.getProduto().getPreco() * this.getQuantidade();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) &&
                Objects.equals(quantidade, pedido.quantidade) &&
                Objects.equals(valor, pedido.valor) &&
                Objects.equals(participante, pedido.participante) &&
                Objects.equals(produto, pedido.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, valor, participante, produto);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pedido{");
        sb.append("id=").append(id);
        sb.append(", quantidade=").append(quantidade);
        sb.append(", valor=").append(valor);
        sb.append(", participante=").append(participante);
        sb.append(", produto=").append(produto);
        sb.append('}');
        return sb.toString();
    }
}
