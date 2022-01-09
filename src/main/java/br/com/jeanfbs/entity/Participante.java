package br.com.jeanfbs.entity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_participante")
public class Participante {

    @Id
    @Column(name = "id_participante")
    private Long id;
    private String nome;
    private String telefone;
    private String nascimento;
    private Boolean status = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participante")
    private List<Credito> creditos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participante")
    private List<Pedido> pedidos = new ArrayList<>();


    public Participante() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

    public Double totalCreditos(){

        Double creditos = getCreditos().stream().mapToDouble(o -> o.getValor()).sum();
        Double pedidos = getPedidos().stream().mapToDouble(p -> p.getTotaPedido()).sum();
        return creditos - pedidos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Double valorTotalPedidos(){
        return pedidos.stream().mapToDouble(o -> o.getValor()).sum();
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    public Boolean isBelowAge() throws ParseException {
        LocalDate minimumAge = LocalDate.of(2000, 01, 01);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = df.parse(this.getNascimento());
        return parseDate.after(Date.from(minimumAge.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participante that = (Participante) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(nascimento, that.nascimento) &&
                Objects.equals(status, that.status) &&
                Objects.equals(creditos, that.creditos) &&
                Objects.equals(pedidos, that.pedidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, telefone, nascimento, status, creditos, pedidos);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Participante{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", telefone='").append(telefone).append('\'');
        sb.append(", nascimento='").append(nascimento).append('\'');
        sb.append(", status=").append(status);
        sb.append(", creditos=").append(creditos);
        sb.append(", pedidos=").append(pedidos);
        sb.append('}');
        return sb.toString();
    }
}
