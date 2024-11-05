package br.com.projeto.todolist.user.pub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsManager {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity ArgumentErrorException (MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                errors.stream().map(ErrorValidationData::new).toList());
    }

    private record ErrorValidationData(String field, String message) {
        public ErrorValidationData(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
