package dev.luisjohann.ofxpermissionchecker.util;

import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthUtil {
    public static AuthUserDetail getUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();

            String name = jwt.getClaimAsString("name"); // Nome de usuário do JWT
            String email = jwt.getClaimAsString("email"); // Email do JWT
//            String roles = jwt.getClaimAsString("realm_access"); // Roles (como exemplo)

            return new AuthUserDetail(authentication.getName(), email, name);
        }
        throw new UnauthorizedException("Sem autorização");
    }
}
