package dev.luisjohann.ofxpermissionchecker.exceptions;

import dev.luisjohann.ofxpermissionchecker.exceptions.config.ErrorResponse;
import lombok.Getter;

public class UnauthorizedException extends RuntimeException {

   @Getter
   ErrorResponse response;

   public UnauthorizedException(String message) {
      super(message);
      this.response = new ErrorResponse("Acesso negado", message);
   }

}
