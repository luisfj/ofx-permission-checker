package dev.luisjohann.ofxpermissionchecker.service;

import org.springframework.stereotype.Service;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;

@Service
public class CheckPermissionService implements ICheckPermissionService {

   private static final String UNSUPPORTED_TYPE_MESSAGE = "O tipo de permissão '%s' não possui suporte!";
   private static final String UNAUTHORIZED_IMPORT_OFX_MESSAGE = "Você não possui autorização para importar arquivos OFX para a unidade %s!";
   private static final String UNAUTHORIZED_GET_DATA_MESSAGE = "Você não possui autorização para visualizar dados da unidade %s!";
   private static final String UNAUTHORIZED_POST_DATA_MESSAGE = "Você não possui autorização para salvar dados da unidade %s!";

   @Override
   public void checkPermission(EventPermissionType type, Long userId, Long ueId) throws UnauthorizedException {
      switch (type) {
         case IMPORT_OFX -> validateImportOfxPermission(userId, ueId);
         case GET_DATA -> validateGetDataPermission(userId, ueId);
         case POST_DATA -> validatePostDataPermission(userId, ueId);
         default -> throw new UnsupportedOperationException(String.format(UNSUPPORTED_TYPE_MESSAGE, type));
      }
   }

   private void validateImportOfxPermission(Long userId, Long ueId) {
      // if (RandomUtil.getPositiveInt() % 2 == 0)
      // throw new
      // UnauthorizedException(String.format(UNAUTHORIZED_IMPORT_OFX_MESSAGE, ueId));
   }

   private void validateGetDataPermission(Long userId, Long ueId) {
      // if (RandomUtil.getPositiveInt() % 2 == 0)
      // throw new UnauthorizedException(String.format(UNAUTHORIZED_GET_DATA_MESSAGE,
      // ueId));
   }

   private void validatePostDataPermission(Long userId, Long ueId) {
      validateGetDataPermission(userId, ueId);
      // if (RandomUtil.getPositiveInt() % 2 == 0)
      // throw new UnauthorizedException(String.format(UNAUTHORIZED_POST_DATA_MESSAGE,
      // ueId));
   }

}
