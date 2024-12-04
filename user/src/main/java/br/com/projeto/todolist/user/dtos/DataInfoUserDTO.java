package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;

import java.time.LocalDate;

public record DataInfoUserDTO(Long id, String username, String email, String firstName, String lastName, String cpf) {

    public static DataInfoUserDTO fromInfoUserDTO(User user) {

        return new DataInfoUserDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCpf());
    }
}

