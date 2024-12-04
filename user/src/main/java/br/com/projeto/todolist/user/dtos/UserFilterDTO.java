package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;

public record UserFilterDTO(
    Long id,
    String username,
    String email,
    String cpf) {

    public static UserFilterDTO fromUserFilterDTO(User user) {
        return new UserFilterDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCpf());
    }

}
