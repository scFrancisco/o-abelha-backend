package com.example.oabelha.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class New {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String titulo;
    @Column(length = 1000)
    private String descripcion;
    @Column(length = 10000)
    private String corpoNoticia;

    @Column
    private String imagemCapa;

    @Column()
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCorpoNoticia() { return corpoNoticia; }
    public void setCorpoNoticia(String corpoNoticia) { this.corpoNoticia = corpoNoticia;}

    public String getDescripcion() { return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getImagemCapa() { return imagemCapa; }
    public void setImagemCapa(String imagemCapa) { this.imagemCapa = imagemCapa; }
}
