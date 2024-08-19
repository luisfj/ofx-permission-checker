package dev.luisjohann.ofxpermissionchecker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CheckPermissionController {

      final ICheckPermissionService checkPermissionService;

      // public String getUserDetails(Authentication authentication) {
      // if (authentication.getPrincipal() instanceof Jwt) {
      // Jwt jwt = (Jwt) authentication.getPrincipal();
      // // Acessar dados do JWT
      // String username = jwt.getClaimAsString("preferred_username"); // Nome de
      // usuário do JWT
      // String email = jwt.getClaimAsString("email"); // Email do JWT
      // String roles = jwt.getClaimAsString("realm_access"); // Roles (como exemplo)

      // // Pode-se acessar outros claims conforme necessário
      // return String.format("User: %s, Email: %s, Roles: %s", username, email,
      // roles);
      // } else {
      // return "No JWT token found";
      // }
      // }

      @GetMapping("/check-import-ofx/{user_id}/{ue_id}")
      public void checkImportOfxPermission(
                  @PathVariable("user_id") Long userId,
                  @PathVariable("ue_id") Long ueId) {
            System.out.println(
                        "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                                    + EventPermissionType.IMPORT_OFX);
            checkPermissionService.checkPermission(EventPermissionType.IMPORT_OFX, userId, ueId);
      }

      @GetMapping("/check-get-data/{user_id}/{ue_id}")
      public void checkGetDataPermission(
                  @PathVariable("user_id") Long userId,
                  @PathVariable("ue_id") Long ueId) {
            System.out.println(
                        "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                                    + EventPermissionType.GET_DATA);
            checkPermissionService.checkPermission(EventPermissionType.GET_DATA, userId, ueId);
      }

      @GetMapping("/check-post-data/{user_id}/{ue_id}")
      public void checkPostDataPermission(
                  @PathVariable("user_id") Long userId,
                  @PathVariable("ue_id") Long ueId) {
            System.out.println(
                        "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                                    + EventPermissionType.POST_DATA);
            checkPermissionService.checkPermission(EventPermissionType.POST_DATA, userId, ueId);
      }
}
