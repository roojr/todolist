package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;

public record UserResponseDTO(
        Long id,
        String username,
        String email) {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
