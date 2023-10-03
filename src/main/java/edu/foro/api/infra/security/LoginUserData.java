package edu.foro.api.infra.security;

public record LoginUserData(String login, String password_hash) {
}
