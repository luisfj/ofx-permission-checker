package dev.luisjohann.ofxpermissionchecker.dto;

import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;

public record UserUeDetailDTO(
            Long userId,
            String email,
            boolean administrator,
            boolean permissionRead,
            boolean permissionWrite,
            boolean permissionImport,
            StatusUser status) {

}
