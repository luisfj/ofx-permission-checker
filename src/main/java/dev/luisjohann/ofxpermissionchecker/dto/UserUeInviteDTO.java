package dev.luisjohann.ofxpermissionchecker.dto;

public record UserUeInviteDTO(
      String email,
      boolean administrator,
      boolean permissionRead,
      boolean permissionWrite,
      boolean permissionImport) {

}
