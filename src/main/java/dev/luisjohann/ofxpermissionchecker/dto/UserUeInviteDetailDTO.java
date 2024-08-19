package dev.luisjohann.ofxpermissionchecker.dto;

import dev.luisjohann.ofxpermissionchecker.enums.StatusUserInvite;

public record UserUeInviteDetailDTO(
      Long id,
      String email,
      boolean administrator,
      boolean permissionRead,
      boolean permissionWrite,
      boolean permissionImport,
      StatusUserInvite status) {

}
