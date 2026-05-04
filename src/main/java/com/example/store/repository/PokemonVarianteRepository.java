package com.example.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.model.PokemonVariante;

public interface PokemonVarianteRepository extends JpaRepository<PokemonVariante, Long> {
}