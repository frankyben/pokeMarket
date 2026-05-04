package com.example.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
