package dev.luisjohann.ofxpermissionchecker.controller;

import dev.luisjohann.ofxpermissionchecker.dto.UserRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import dev.luisjohann.ofxpermissionchecker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserManagementController {

    final UserService userService;
    final ICheckPermissionService checkPermissionService;

    @PostMapping("/public/user/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserRegisterDTO newUserDto) {
        userService.createNewUser(newUserDto);
    }

    @GetMapping("/public/check-test/{ue_id}")
    public void checkImportOfxPermission(
            @PathVariable("ue_id") Long ueId) {
        log.info(
                "****************** CHECK PERMISSION:  udId:{} for eventType:{}",
                ueId, EventPermissionType.IMPORT_OFX);
        checkPermissionService.checkPermission(EventPermissionType.IMPORT_OFX, ueId);
    }

}
