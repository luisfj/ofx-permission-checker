package dev.luisjohann.ofxpermissionchecker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailDTO;
import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.AuthService;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import dev.luisjohann.ofxpermissionchecker.service.UserUeInviteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ue")
@RequiredArgsConstructor
public class UeInvitesController {
   final UserUeInviteService userUeInviteService;
   final ICheckPermissionService checkPermissionService;
   final AuthService authService;

   @PostMapping("/invite/{ueId}")
   @ResponseStatus(HttpStatus.CREATED)
   public void inviteUser(@PathVariable("ueId") Long ueId, @RequestBody UserUeInviteDTO dto) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);

      userUeInviteService.inviteUser(dto, ueId);
   }

   @PostMapping("/invite/confirm/{inviteId}")
   @ResponseStatus(HttpStatus.OK)
   public void confirmInvite(@PathVariable("inviteId") Long inviteId) {
      userUeInviteService.confirmInvite(inviteId);
   }

   @PostMapping("/invite/reject/{inviteId}")
   @ResponseStatus(HttpStatus.OK)
   public void rejectInvite(@PathVariable("inviteId") Long inviteId) {
      userUeInviteService.rejectInvite(inviteId);
   }

   @DeleteMapping("/invite/{ueId}/{inviteId}")
   @ResponseStatus(HttpStatus.OK)
   public void deleteInvite(@PathVariable("ueId") Long ueId, @PathVariable("inviteId") Long inviteId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);
      userUeInviteService.deleteInvite(ueId, inviteId);
   }

   @GetMapping("/invites/{ueId}")
   @ResponseStatus(HttpStatus.OK)
   public List<UserUeInviteDetailDTO> findUeInvites(@PathVariable("ueId") Long ueId) {
      checkPermissionService.checkPermission(EventPermissionType.INVITE, authService.getLoggedUserId(), ueId);

      return userUeInviteService.findUeInvites(ueId);
   }

   @GetMapping("/invites/")
   @ResponseStatus(HttpStatus.OK)
   public List<UserUeInviteDetailDTO> findMyInvites() {
      return userUeInviteService.findMyInvites();
   }

}
