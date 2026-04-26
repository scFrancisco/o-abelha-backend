package com.example.oabelha.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscricao {

    public enum Estado { PENDENTE, ACEITE, REJEITADO, CONFIRMADO, EXPIRADO, CANCELADO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id", nullable = false)
    private Event evento;

    private String nome;
    private String email;
    private String telefone;
    private String metodoPagamento;
    private Integer numPessoas = 1;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDENTE;

    @Column(unique = true)
    private String referenciaPagamento;

    @Column(unique = true)
    private String codigoConfirmacao;

    private Double valorTotal;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiraEm;
    private LocalDateTime pagoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Event getEvento() { return evento; }
    public void setEvento(Event evento) { this.evento = evento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public Integer getNumPessoas() { return numPessoas; }
    public void setNumPessoas(Integer numPessoas) { this.numPessoas = numPessoas; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public String getReferenciaPagamento() { return referenciaPagamento; }
    public void setReferenciaPagamento(String referenciaPagamento) { this.referenciaPagamento = referenciaPagamento; }

    public String getCodigoConfirmacao() { return codigoConfirmacao; }
    public void setCodigoConfirmacao(String codigoConfirmacao) { this.codigoConfirmacao = codigoConfirmacao; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiraEm() { return expiraEm; }
    public void setExpiraEm(LocalDateTime expiraEm) { this.expiraEm = expiraEm; }

    public LocalDateTime getPagoEm() { return pagoEm; }
    public void setPagoEm(LocalDateTime pagoEm) { this.pagoEm = pagoEm; }
}
