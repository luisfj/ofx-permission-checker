package dev.luisjohann.ofxpermissionchecker.dto;

public record UeUserCanAccessDTO(
        Long createdUserId,
        String createdEmail,
        Long ueId,
        String ueName,
        String color) {

}
