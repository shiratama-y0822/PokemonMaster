package com.shiratama.pokemonmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRelationsRepository
                extends JpaRepository<TypeRelation, TypeRelationId> {
}