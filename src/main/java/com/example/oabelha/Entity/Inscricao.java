package com.example.oabelha.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscricao {

    public enum Estado { PENDENTE, CONFIRMADO, EXPIRADO, CANCELADO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Event evento;

    private String nome;
    private String email;
    private Integer numPessoas = 1;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDENTE;

    // Referência única enviada ao utilizador para pagar
    @Column(unique = true)
    private String referenciaPagamento;

    // Código de confirmação enviado por email após pagamento
    @Column(unique = true)
    private String codigoConfirmacao;

    private Double valorTotal;

    private LocalDateTime criadoEm = LocalDateTime.now();
    private LocalDateTime expiraEm;        // criadoEm + 30 min
    private LocalDateTime pagoEm;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Event getEvento() { return evento; }
    public void setEvento(Event evento) { this.evento = evento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

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

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getExpiraEm() { return expiraEm; }
    public void setExpiraEm(LocalDateTime expiraEm) { this.expiraEm = expiraEm; }

    public LocalDateTime getPagoEm() { return pagoEm; }
    public void setPagoEm(LocalDateTime pagoEm) { this.pagoEm = pagoEm; }
}