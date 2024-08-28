package dev.luisjohann.ofxpermissionchecker.service;

import dev.luisjohann.ofxpermissionchecker.dto.UserRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.exceptions.UsuarioJaExisteException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakService {
   final Keycloak keycloakClient;

   @Value("${keycloak.realm}")
   String realm;

   public String addUser(UserRegisterDTO dto) {
      var userRep = new org.keycloak.representations.idm.UserRepresentation();
      userRep.setEmail(dto.email());
      userRep.setUsername(dto.email());
      userRep.setEnabled(Boolean.TRUE);
      userRep.setEmailVerified(Boolean.TRUE);

      var credential = new CredentialRepresentation();
      credential.setTemporary(false);
      credential.setType(CredentialRepresentation.PASSWORD);
      credential.setValue(dto.password());

      userRep.setCredentials(List.of(credential));

      var response = keycloakClient.realm(realm).users().create(userRep);

      if (response.getStatus() == HttpStatus.SC_CREATED) {
         var pathSplit = response.getLocation().getPath().split("/");

         return pathSplit[pathSplit.length - 1];
      }
      if (response.getStatus() == HttpStatus.SC_CONFLICT)
         throw new UsuarioJaExisteException();
      throw new RuntimeException(response.getStatusInfo().toString());
   }

   public void removerUser() {
      var response = keycloakClient.realm(realm).users().delete("3d6ea872-7ed2-46c7-990f-684405ebad83");

      System.out.println("---------- deleted User USER ------------");

      if (response.getStatus() != HttpStatus.SC_NO_CONTENT) {
         throw new RuntimeException(response.getStatusInfo().toString());
      }
   }

   public UserRepresentation findUserDetailsByEmail(String email) {
      var response = keycloakClient.realm(realm)
              .users()
              .searchByEmail(email, true);

      return response.stream().findFirst().orElseThrow(() -> new UnauthorizedException("Usuário não cadastrado!"));

   }
}
