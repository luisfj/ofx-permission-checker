package dev.luisjohann.ofxpermissionchecker.service;

import dev.luisjohann.ofxpermissionchecker.dto.SseUserChangeMessageDTO;
import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;
import dev.luisjohann.ofxpermissionchecker.model.UserEntity;
import dev.luisjohann.ofxpermissionchecker.queue.QueueSenderUserChange;
import dev.luisjohann.ofxpermissionchecker.repository.UserRepository;
import dev.luisjohann.ofxpermissionchecker.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    final UserRepository userRepository;
    final KeycloakService keycloakService;
    final QueueSenderUserChange queueSenderUserChange;

    public UserEntity getLoggedUser() {
        return userRepository
                .findByIdKeyCloack(AuthUtil.getUserDetails().keycloakId())
                .orElseGet(this::registerNewAndGet);
    }

    private UserEntity registerNewAndGet() {
        var credentials = AuthUtil.getUserDetails();

        var userDetails = keycloakService.findUserDetailsByEmail(credentials.email());

        var user = new UserEntity();
        user.setEmail(userDetails.getEmail());
        user.setName(credentials.name());
        user.setStatus(StatusUser.ACTIVE);
        user.setIdKeyCloack(credentials.keycloakId());
        user.setCreatedAt(LocalDateTime.now());
        log.info("Usuário incluído na base de dados, nome: {} email: {}", credentials.name(), credentials.email());
        var registeredUser = userRepository.save(user);

        queueSenderUserChange.send(new SseUserChangeMessageDTO(registeredUser.getIdKeyCloack(), registeredUser.getEmail(), registeredUser.getName()));
        return registeredUser;
    }

    public Long getLoggedUserId() {
        return getLoggedUser().getId();
    }

    public String getLoggedUserEmail() {
        return getLoggedUser().getEmail();
    }


}
