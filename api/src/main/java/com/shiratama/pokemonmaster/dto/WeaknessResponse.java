package com.shiratama.pokemonmaster.dto;

import java.math.BigDecimal;

public record WeaknessResponse(
    Integer pokemonId,
    Integer attackTypeId,
    BigDecimal multiplier) {
}