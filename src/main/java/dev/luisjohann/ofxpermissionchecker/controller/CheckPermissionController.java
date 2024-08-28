package dev.luisjohann.ofxpermissionchecker.controller;

import dev.luisjohann.ofxpermissionchecker.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.service.ICheckPermissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CheckPermissionController {

    final ICheckPermissionService checkPermissionService;

    @GetMapping("/check-import-ofx/{user_id}/{ue_id}")
    public void checkImportOfxPermission(
            @PathVariable("ue_id") Long ueId) {
        log.info("** Check permission: user: {} ueId: {} for eventType: {}",
                AuthUtil.getUserDetails().email(), ueId, EventPermissionType.IMPORT_OFX);
        checkPermissionService.checkPermission(EventPermissionType.IMPORT_OFX, ueId);
    }

    @GetMapping("/check-get-data/{ue_id}")
    public void checkGetDataPermission(
            @PathVariable("ue_id") Long ueId) {
        log.info("** Check permission: user: {} ueId: {} for eventType: {}",
                AuthUtil.getUserDetails().email(), ueId, EventPermissionType.GET_DATA);
        checkPermissionService.checkPermission(EventPermissionType.GET_DATA, ueId);
    }

    @GetMapping("/check-post-data/{ue_id}")
    public void checkPostDataPermission(
            @PathVariable("ue_id") Long ueId) {
        log.info("** Check permission: user: {} ueId: {} for eventType: {}",
                AuthUtil.getUserDetails().email(), ueId, EventPermissionType.POST_DATA);
        checkPermissionService.checkPermission(EventPermissionType.POST_DATA, ueId);
    }
}
