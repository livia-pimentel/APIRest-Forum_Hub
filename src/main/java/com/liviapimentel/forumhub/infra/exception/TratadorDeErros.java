package com.liviapimentel.forumhub.infra.exception;

import com.liviapimentel.forumhub.infra.exception.dto.DadosErroMensagem;
import com.liviapimentel.forumhub.infra.exception.dto.DadosErroValidacao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErroMensagem("Registro não encontrado ou inativo."));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream()
                .map(DadosErroValidacao::new)
                .toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErroConflito() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Dados já cadastrados no sistema.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            DisabledException.class,
            InternalAuthenticationServiceException.class // Captura usuário inexistente
    })

    public ResponseEntity tratarErrosAutenticacao(Exception ex) {
        if (ex instanceof DisabledException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário inativo");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário inexistente ou senha inválida");
    }
}
