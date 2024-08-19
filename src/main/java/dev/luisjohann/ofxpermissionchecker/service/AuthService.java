package dev.luisjohann.ofxpermissionchecker.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.model.UserEntity;
import dev.luisjohann.ofxpermissionchecker.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

   final UserRepository userRepository;

   public UserEntity getLoggedUser() {
      var authentication = SecurityContextHolder.getContext().getAuthentication();
      var keycloakId = authentication.getName();

      return userRepository
            .findByIdKeyCloack(keycloakId)
            .orElseThrow(() -> new UnauthorizedException("Usuário sem acesso!"));
   }

   public Long getLoggedUserId() {
      return getLoggedUser().getId();
   }

   public String getLoggedUserEmail() {
      return getLoggedUser().getEmail();
   }

}
