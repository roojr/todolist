package br.com.projeto.todolist.user.repositories;

import br.com.projeto.todolist.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    List<Optional<User>> findUsersByUsernameContainingIgnoreCase(String username);

    @Query("select u from User u where u.username = :username")
    UserDetails findUserDetailsByLogin(String username);

    UserDetails findUserById(Long id);

    List<Optional<User>> findUserByEmailContainingIgnoreCase(String email);

    Optional<User> findUserByCpf(String cpf);

}
