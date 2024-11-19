package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;

public record UserComponentDTO(
        Long id,
        String username) {

    public static UserComponentDTO fromDTO(User user) {
        return new UserComponentDTO(user.getId(), user.getUsername());
    }
}
