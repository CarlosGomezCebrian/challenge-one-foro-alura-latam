package edu.foro.api.infra.security;

import jakarta.validation.constraints.NotBlank;


public record LoginUserData(@NotBlank String login, @NotBlank String password_hash) {
}
