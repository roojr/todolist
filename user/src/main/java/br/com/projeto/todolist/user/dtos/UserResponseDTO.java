package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;

public record UserResponseDTO(
        Long id,
        String login,
        String firsName,
        String lastName) {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName());
    }
}
