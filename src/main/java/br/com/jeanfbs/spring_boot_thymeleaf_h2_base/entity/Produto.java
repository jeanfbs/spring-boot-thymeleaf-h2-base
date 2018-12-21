package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;
    private String descricao;
    private Double preco;
    private Boolean status = true;


    public Produto(Produto produto){
        this.id = produto.id;
        this.descricao = produto.descricao;
        this.preco = produto.preco;
        this.status = produto.status;
    }


    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(preco, produto.preco) &&
                Objects.equals(status, produto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, preco, status);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Produto{");
        sb.append("id=").append(id);
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", preco=").append(preco);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
