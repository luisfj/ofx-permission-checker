package dev.luisjohann.ofxpermissionchecker.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(@NotBlank String email, @NotBlank String password) {
   
}
