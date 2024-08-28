package dev.luisjohann.ofxpermissionchecker.dto;

import dev.luisjohann.ofxpermissionchecker.enums.StatusActiveInactive;

public record SseUeChangeMessageDTO(
        Long id,
        String name,
        StatusActiveInactive status) {

}
