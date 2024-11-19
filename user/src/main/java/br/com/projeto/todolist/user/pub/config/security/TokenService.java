package br.com.projeto.todolist.user.pub.config.security;

import br.com.projeto.todolist.user.dtos.TokenInfo;
import br.com.projeto.todolist.user.dtos.UserComponentDTO;
import br.com.projeto.todolist.user.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public TokenInfo generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expires = genExpirationDate();
            String token  = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(expires)
                    .sign(algorithm);

            return new TokenInfo(token, expires, UserComponentDTO.fromDTO(user));
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
            return subject;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error while validating token", exception);
        }
    }

    public String recoverToken(HttpServletRequest token) {
        var authHeader = token.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


    private Instant genExpirationDate() { return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));}

}
