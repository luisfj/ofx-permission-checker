package dev.luisjohann.ofxpermissionchecker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.dto.UserRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import dev.luisjohann.ofxpermissionchecker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

   final UserService userService;
   final ICheckPermissionService checkPermissionService;

   @PostMapping("/public/user/new")
   @ResponseStatus(HttpStatus.CREATED)
   public void createUser(@RequestBody @Valid UserRegisterDTO newUserDto) {
      userService.createNewUser(newUserDto);
   }

   @GetMapping("/public/check-test/{user_id}/{ue_id}")
   public void checkImportOfxPermission(
         @PathVariable("user_id") Long userId,
         @PathVariable("ue_id") Long ueId) {
      System.out.println(
            "****************** CHECK PERMISSION: userid:" + userId + " udId:" + ueId + " for eventType:"
                  + EventPermissionType.IMPORT_OFX);
      checkPermissionService.checkPermission(EventPermissionType.IMPORT_OFX, userId, ueId);
   }

}
