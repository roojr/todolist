package br.com.projeto.todolist.user.dtos;

import java.time.Instant;
import java.util.Map;

public record TokenInfo(
        String token,
        Instant expiresIn,
        UserComponentDTO user) {
}
