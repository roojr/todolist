package br.com.projeto.todolist.user.repositories;

import br.com.projeto.todolist.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    @Query("select u from User u where u.login = :login")
    UserDetails findUserDetailsByLogin(String login);

    UserDetails findUserById(Long id);
}
