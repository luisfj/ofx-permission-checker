package dev.luisjohann.ofxpermissionchecker.service;

import dev.luisjohann.ofxpermissionchecker.enums.EventPermissionType;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;

public interface ICheckPermissionService {

   void checkPermission(EventPermissionType type, Long userId, Long ueId) throws UnauthorizedException;
}
