package dev.luisjohann.ofxpermissionchecker.dto;

public record UserUeUpdateDTO(
      boolean administrator,
      boolean permissionRead,
      boolean permissionWrite,
      boolean permissionImport) {

}
