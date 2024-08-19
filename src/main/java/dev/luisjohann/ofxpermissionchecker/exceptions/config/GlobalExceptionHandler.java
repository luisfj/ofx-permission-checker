package dev.luisjohann.ofxpermissionchecker.exceptions.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.luisjohann.ofxpermissionchecker.exceptions.ImportOfxException;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.exceptions.UsuarioJaExisteException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(UnauthorizedException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ResponseBody
   public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
      return ex.getResponse();
   }

   @ExceptionHandler(ImportOfxException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ResponseBody
   public ErrorResponse handleDefaultException(ImportOfxException ex) {
      return ex.getResponse();
   }

   @ExceptionHandler(UsuarioJaExisteException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   @ResponseBody
   public ErrorResponse handleConflictException(UsuarioJaExisteException ex) {
      return ex.getResponse();
   }

   @ExceptionHandler(UnsupportedOperationException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorResponse handleUnsupportedOperationException(UnsupportedOperationException ex) {
      return new ErrorResponse("Ação não suportada", ex.getMessage());
   }
}
