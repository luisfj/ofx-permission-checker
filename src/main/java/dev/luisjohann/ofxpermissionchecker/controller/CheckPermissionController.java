package dev.luisjohann.ofxpermissionchecker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CheckPermissionController {

   final ICheckPermissionService checkPermissionService;

   @GetMapping("/check-import-ofx/{user_id}/{ue_id}")
   public Mono<Void> checkImportOfxPermission(
         @PathVariable("user_id") Long userId,
         @PathVariable("ue_id") Long ueId) {
      System.out.println(
            "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                  + EventPermissionType.IMPORT_OFX);
      checkPermissionService.checkPermission(EventPermissionType.IMPORT_OFX, userId, ueId);
      return Mono.empty();
   }

   @GetMapping("/check-get-data/{user_id}/{ue_id}")
   public Mono<Void> checkGetDataPermission(
         @PathVariable("user_id") Long userId,
         @PathVariable("ue_id") Long ueId) {
      System.out.println(
            "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                  + EventPermissionType.GET_DATA);
      checkPermissionService.checkPermission(EventPermissionType.GET_DATA, userId, ueId);
      return Mono.empty();
   }

   @GetMapping("/check-post-data/{user_id}/{ue_id}")
   public Mono<Void> checkPostDataPermission(
         @PathVariable("user_id") Long userId,
         @PathVariable("ue_id") Long ueId) {
      System.out.println(
            "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                  + EventPermissionType.POST_DATA);
      checkPermissionService.checkPermission(EventPermissionType.POST_DATA, userId, ueId);
      return Mono.empty();
   }
}
