package com.shiratama.pokemonmaster.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PartiesPutRequest(
        @NotNull(message = "ボックス番号を入力してください。") @Positive(message = "ボックス番号は1以上で入力してください。") Integer boxId) {
}
