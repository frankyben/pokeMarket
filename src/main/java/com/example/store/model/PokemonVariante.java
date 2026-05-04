package com.example.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PokemonVariante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pokemon pokemon;

    private boolean shiny;
    private boolean legacy;
    private boolean suerte;
    private boolean fondo;

    @Enumerated(EnumType.STRING)
    private Forma forma;

    private Double precio;
    private Integer stock;

    public PokemonVariante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pokemon getPokemon() { return pokemon; }
    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }

    public boolean isShiny() { return shiny; }
    public void setShiny(boolean shiny) { this.shiny = shiny; }

    public boolean isLegacy() { return legacy; }
    public void setLegacy(boolean legacy) { this.legacy = legacy; }

    public boolean isSuerte() { return suerte; }
    public void setSuerte(boolean suerte) { this.suerte = suerte; }

    public boolean isFondo() { return fondo; }
    public void setFondo(boolean fondo) { this.fondo = fondo; }

    public Forma getForma() { return forma; }
    public void setForma(Forma forma) { this.forma = forma; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}