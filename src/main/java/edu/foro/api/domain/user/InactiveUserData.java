package edu.foro.api.domain.user;

import jakarta.validation.constraints.NotNull;

public record InactiveUserData(@NotNull Long id) {
}
