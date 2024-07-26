package dev.luisjohann.ofxpermissionchecker.exceptions.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(UnauthorizedException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ResponseBody
   public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
      return ex.getResponse();
   }

   @ExceptionHandler(UnsupportedOperationException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorResponse handleUnsupportedOperationException(UnsupportedOperationException ex) {
      return new ErrorResponse("Ação não suportada", ex.getMessage());
   }
}
