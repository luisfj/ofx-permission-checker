package dev.luisjohann.ofxpermissionchecker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeDetailDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeUpdateDTO;
import dev.luisjohann.ofxpermissionchecker.dto.map.UserUeMapper;
import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;
import dev.luisjohann.ofxpermissionchecker.exceptions.ImportOfxException;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.model.UeEntity;
import dev.luisjohann.ofxpermissionchecker.model.UserEntity;
import dev.luisjohann.ofxpermissionchecker.model.UserUeEntity;
import dev.luisjohann.ofxpermissionchecker.model.UserUeInviteEntity;
import dev.luisjohann.ofxpermissionchecker.repository.UserUeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUeService {

   final UserUeRepository userUeRepository;
   final AuthService authService;

   public void inviteUserAsAdminToUe(UserEntity user, UeEntity ue) {
      var userPermission = new UserUeEntity();
      userPermission.setAdministrator(Boolean.TRUE);
      userPermission.setCreatedAt(LocalDateTime.now());
      userPermission.setCreatedBy(authService.getLoggedUser());
      userPermission.setPermissionImport(Boolean.TRUE);
      userPermission.setPermissionRead(Boolean.TRUE);
      userPermission.setPermissionWrite(Boolean.TRUE);
      userPermission.setStatus(StatusUser.ACTIVE);
      userPermission.setUe(ue);
      userPermission.setUser(user);

      userUeRepository.save(userPermission);
   }

   public void addAccessFromConfimedInvite(UserUeInviteEntity invite, UserEntity userGainAccess) {
      if (!userGainAccess.getEmail().equals(invite.getEmail()))
         throw new UnauthorizedException("Não é possível atribuir acesso, emails incompatíveis.");

      var userPermission = new UserUeEntity();
      userPermission.setAdministrator(invite.isAdministrator());
      userPermission.setCreatedAt(LocalDateTime.now());
      userPermission.setCreatedBy(invite.getCreatedBy());
      userPermission.setPermissionImport(invite.isPermissionImport());
      userPermission.setPermissionRead(invite.isPermissionRead());
      userPermission.setPermissionWrite(invite.isPermissionWrite());
      userPermission.setStatus(StatusUser.ACTIVE);
      userPermission.setUe(invite.getUe());
      userPermission.setUser(userGainAccess);

      userUeRepository.save(userPermission);
   }

   public boolean existsByUserEmailAndUeId(String userEmail, Long ueId) {
      return userUeRepository.existsByUserEmailAndUeId(userEmail, ueId);
   }

   public void removeUserAccess(Long ueId, Long userId) {
      if (!userUeRepository.existsByUserIdNotAndUeIdAndAdministratorTrue(userId, ueId))
         throw new ImportOfxException("Não é possível remover usuário se este for o ultimo administrador");

      var deletePermission = userUeRepository.findByUserIdAndUeId(userId, ueId)
            .orElseThrow(() -> new ImportOfxException("Não existe permissão com os dados informados"));
      userUeRepository.delete(deletePermission);
   }

   public List<UserUeDetailDTO> findUeUsers(Long ueId) {
      var permitedUsersDetails = userUeRepository.findByUeId(ueId);

      return UserUeMapper.INSTANCE.entityToListDTO(permitedUsersDetails);
   }

   public void activateUserAccess(Long ueId, Long userId) {
      var permission = userUeRepository.findByUserIdAndUeId(userId, ueId)
            .orElseThrow(() -> new ImportOfxException("Não existe permissão com os dados informados"));

      permission.setStatus(StatusUser.ACTIVE);
      userUeRepository.save(permission);
   }

   public void inactivateUserAccess(Long ueId, Long userId) {
      if (!userUeRepository.existsByUserIdNotAndUeIdAndAdministratorTrue(userId, ueId))
         throw new ImportOfxException("Não é possível inativar o usuário se este for o ultimo administrador");

      var permission = userUeRepository.findByUserIdAndUeId(userId, ueId)
            .orElseThrow(() -> new ImportOfxException("Não existe permissão com os dados informados"));

      permission.setStatus(StatusUser.INACTIVE);
      userUeRepository.save(permission);
   }

   public void updateUserAccess(Long ueId, Long userId, UserUeUpdateDTO dto) {
      var updateUserPermission = userUeRepository.findByUserIdAndUeId(userId, ueId)
            .orElseThrow(() -> new ImportOfxException("Não existe permissão com os dados informados"));

      if (!dto.administrator() && updateUserPermission.isAdministrator() 
      && !userUeRepository.existsByUserIdNotAndUeIdAndAdministratorTrue(userId, ueId))
         throw new ImportOfxException("Não é possível remover usuário de administrador se este for o ultimo administrador");

         updateUserPermission.setAdministrator(dto.administrator());
         updateUserPermission.setPermissionImport(dto.permissionImport());
         updateUserPermission.setPermissionRead(dto.permissionRead());
         updateUserPermission.setPermissionWrite(dto.permissionWrite());
         
      userUeRepository.save(updateUserPermission);
   }

}
