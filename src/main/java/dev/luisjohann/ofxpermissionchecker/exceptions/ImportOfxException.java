package dev.luisjohann.ofxpermissionchecker.exceptions;

import dev.luisjohann.ofxpermissionchecker.exceptions.config.ErrorResponse;
import lombok.Getter;

public class ImportOfxException extends RuntimeException {
   @Getter
   ErrorResponse response;

   public ImportOfxException(String message) {
      super(message);
      this.response = new ErrorResponse("Erro", message);
   }
}
