package br.com.projeto.todolist.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Please provide login") String login,
        @NotBlank(message = "Please provide password") String password) {
}
