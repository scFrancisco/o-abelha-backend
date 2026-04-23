package com.example.oabelha.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(length = 5000)
    private String corpoEvento;

    private LocalDateTime dataEvento;

    private String local;

    private String imagemCapa;

    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer capacidade;      // máximo de inscritos
    private Double preco;            // 0.0 = gratuito
    private Boolean inscricoesAtivas = true;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCorpoEvento() { return corpoEvento; }
    public void setCorpoEvento(String corpoEvento) { this.corpoEvento = corpoEvento; }

    public LocalDateTime getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getImagemCapa() { return imagemCapa; }
    public void setImagemCapa(String imagemCapa) { this.imagemCapa = imagemCapa; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getInscricoesAtivas() {
        return inscricoesAtivas;
    }

    public void setInscricoesAtivas(Boolean inscricoesAtivas) {
        this.inscricoesAtivas = inscricoesAtivas;
    }
}