package dev.luisjohann.ofxpermissionchecker.controller;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailDTO;
import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import dev.luisjohann.ofxpermissionchecker.service.UserUeInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ue")
@RequiredArgsConstructor
public class UeInvitesController {
    final UserUeInviteService userUeInviteService;
    final ICheckPermissionService checkPermissionService;

    @PostMapping("/invite/{ueId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void inviteUser(@PathVariable("ueId") Long ueId, @RequestBody UserUeInviteDTO dto) {
        checkPermissionService.checkPermission(EventPermissionType.INVITE, ueId);

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
        checkPermissionService.checkPermission(EventPermissionType.INVITE, ueId);
        userUeInviteService.deleteInvite(ueId, inviteId);
    }

    @GetMapping("/invites/{ueId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserUeInviteDetailDTO> findUeInvites(@PathVariable("ueId") Long ueId) {
        checkPermissionService.checkPermission(EventPermissionType.INVITE, ueId);

        return userUeInviteService.findUeInvites(ueId);
    }

    @GetMapping("/invites/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserUeInviteDetailDTO> findMyInvites() {
        return userUeInviteService.findMyInvites();
    }

}
