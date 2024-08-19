package dev.luisjohann.ofxpermissionchecker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.dto.UeRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeDetailDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeUpdateDTO;
import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.AuthService;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import dev.luisjohann.ofxpermissionchecker.service.UeService;
import dev.luisjohann.ofxpermissionchecker.service.UserUeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ue")
@RequiredArgsConstructor
public class UeController {
   final UeService ueService;
   final UserUeService userUeService;
   final ICheckPermissionService checkPermissionService;
   final AuthService authService;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public void criarUe(@RequestBody UeRegisterDTO dto) {
      ueService.createUe(dto);
   }

   @DeleteMapping("/users/{ueId}/{userId}")
   @ResponseStatus(HttpStatus.OK)
   public void removeUserAccess(@PathVariable("ueId") Long ueId, @PathVariable("userId") Long userId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);
      userUeService.removeUserAccess(ueId, userId);
   }

   @PostMapping("/users/activate/{ueId}/{userId}")
   @ResponseStatus(HttpStatus.OK)
   public void activateUserAccess(@PathVariable("ueId") Long ueId, @PathVariable("userId") Long userId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);
      userUeService.activateUserAccess(ueId, userId);
   }

   @PostMapping("/users/inactivate/{ueId}/{userId}")
   @ResponseStatus(HttpStatus.OK)
   public void inactivateUserAccess(@PathVariable("ueId") Long ueId, @PathVariable("userId") Long userId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);
      userUeService.inactivateUserAccess(ueId, userId);
   }

   @PutMapping("/users/{ueId}/{userId}")
   @ResponseStatus(HttpStatus.OK)
   public void updateUserAccess(@PathVariable("ueId") Long ueId, @PathVariable("userId") Long userId,
         @RequestBody UserUeUpdateDTO dto) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);
      userUeService.updateUserAccess(ueId, userId, dto);
   }

   @GetMapping("/users/{ueId}")
   @ResponseStatus(HttpStatus.OK)
   public List<UserUeDetailDTO> findUeInvites(@PathVariable("ueId") Long ueId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);

      return userUeService.findUeUsers(ueId);
   }
}
