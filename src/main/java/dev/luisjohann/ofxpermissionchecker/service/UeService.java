package dev.luisjohann.ofxpermissionchecker.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.luisjohann.ofxpermissionchecker.dto.UeRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.dto.map.UeMapper;
import dev.luisjohann.ofxpermissionchecker.enums.StatusActiveInactive;
import dev.luisjohann.ofxpermissionchecker.repository.UeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UeService {

   final UeRepository ueRepository;
   final UserUeService userUeService;
   final AuthService authService;

   @Transactional
   public void createUe(UeRegisterDTO dto) {
      var newUe = UeMapper.INSTANCE.dtoToEntity(dto);
      
      newUe.setCreatedAt(LocalDateTime.now());
      newUe.setStatus(StatusActiveInactive.ACTIVE);

      var saved = ueRepository.save(newUe);
      userUeService.inviteUserAsAdminToUe(authService.getLoggedUser(), saved);
   }

}
