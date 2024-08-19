package dev.luisjohann.ofxpermissionchecker.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import dev.luisjohann.ofxpermissionchecker.dto.UserRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;
import dev.luisjohann.ofxpermissionchecker.model.UserEntity;
import dev.luisjohann.ofxpermissionchecker.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

   final UserRepository userRepository;
   final KeycloakService keycloakService;

   public void createNewUser(@Valid UserRegisterDTO newUserDto) {
      var newKeycloakUserId = keycloakService.addUser(newUserDto);

      var userOpt = userRepository.findByEmail(newUserDto.email());

      userOpt.ifPresentOrElse(user -> {
         user.setStatus(StatusUser.ACTIVE);
         user.setIdKeyCloack(newKeycloakUserId);
         userRepository.save(user);
      }, () -> {
         var user = new UserEntity();
         user.setEmail(newUserDto.email());
         user.setName(newUserDto.email());
         user.setStatus(StatusUser.ACTIVE);
         user.setIdKeyCloack(newKeycloakUserId);
         user.setCreatedAt(LocalDateTime.now());
         userRepository.save(user);
      });
   }

}
