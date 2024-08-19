package dev.luisjohann.ofxpermissionchecker.exceptions;

import dev.luisjohann.ofxpermissionchecker.exceptions.config.ErrorResponse;
import lombok.Getter;

public class UsuarioJaExisteException extends RuntimeException {

   @Getter
   ErrorResponse response;

   public UsuarioJaExisteException() {
      super("Este endereço de email já foi cadastrado!");
      this.response = new ErrorResponse("Atenção", "Este endereço de email já foi cadastrado!");
   }

}
