package dev.luisjohann.ofxpermissionchecker.service;

import java.util.List;

import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.luisjohann.ofxpermissionchecker.dto.UserRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.exceptions.UsuarioJaExisteException;
import lombok.RequiredArgsConstructor;

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

   public void findByEmail() {
      System.out.println(keycloakClient.realm(realm).users().userProfile());
      var response = keycloakClient.realm(realm).users().searchByEmail("luisfj_pr@hotmail.com", true);

      System.out.println(response);
      System.out.println("---------- find USER ------------");

   }
}
