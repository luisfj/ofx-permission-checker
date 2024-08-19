package dev.luisjohann.ofxpermissionchecker.service;

import org.springframework.stereotype.Component;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.model.UserUeEntity;
import dev.luisjohann.ofxpermissionchecker.repository.UserUeRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@Component
@RequiredArgsConstructor
public class CheckPermissionService implements ICheckPermissionService {

   private static final String UNSUPPORTED_TYPE_MESSAGE = "O tipo de permissão '%s' não possui suporte!";
   private static final String UNAUTHORIZED_DEFAULT_MESSAGE = "Sem autorização para a unidade %s!";
   private static final String UNAUTHORIZED_IMPORT_OFX_MESSAGE = "Sem autorização para importar arquivos OFX para a unidade %s!";
   private static final String UNAUTHORIZED_GET_DATA_MESSAGE = "Sem autorização para visualizar dados da unidade %s!";
   private static final String UNAUTHORIZED_POST_DATA_MESSAGE = "Sem autorização para salvar dados da unidade %s!";
   private static final String UNAUTHORIZED_INVITE_MESSAGE = "Sem autorização para convidar usuários para a unidade %s!";

   final UserUeRepository permissionaRepository;

   @Override
   public void checkPermission(EventPermissionType type, Long userId, Long ueId) throws UnauthorizedException {
      switch (type) {
         case IMPORT_OFX -> validateImportOfxPermission(userId, ueId);
         case GET_DATA -> validateGetDataPermission(userId, ueId);
         case POST_DATA -> validatePostDataPermission(userId, ueId);
         case INVITE -> validateInvitePermission(userId, ueId);
         default -> throw new UnsupportedOperationException(String.format(UNSUPPORTED_TYPE_MESSAGE, type));
      }
   }

   private UserUeEntity getPermission(Long userId, Long ueId) {
      return permissionaRepository.findByUserIdAndUeId(userId, ueId)
            .orElseThrow(() -> new UnauthorizedException(String.format(UNAUTHORIZED_DEFAULT_MESSAGE, ueId)));
   }

   private void validateImportOfxPermission(Long userId, Long ueId) {
      var permission = getPermission(userId, ueId);
      if (!permission.isAdministrator() && !permission.isPermissionImport())
         throw new UnauthorizedException(String.format(UNAUTHORIZED_IMPORT_OFX_MESSAGE, ueId));
   }

   private void validateGetDataPermission(Long userId, Long ueId) {
      var permission = getPermission(userId, ueId);
      if (!permission.isAdministrator() && !permission.isPermissionRead())
         throw new UnauthorizedException(String.format(UNAUTHORIZED_GET_DATA_MESSAGE,
               ueId));
   }

   private void validatePostDataPermission(Long userId, Long ueId) {
      var permission = getPermission(userId, ueId);
      if (!permission.isAdministrator() && !permission.isPermissionWrite())
         throw new UnauthorizedException(String.format(UNAUTHORIZED_POST_DATA_MESSAGE,
               ueId));
   }

   private void validateInvitePermission(Long userId, Long ueId) {
      if (!getPermission(userId, ueId).isAdministrator())
         throw new UnauthorizedException(String.format(UNAUTHORIZED_INVITE_MESSAGE,
               ueId));
   }

}
