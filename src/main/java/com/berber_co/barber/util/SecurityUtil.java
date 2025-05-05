package com.berber_co.barber.util;

import com.berber_co.barber.entity.User;
import com.berber_co.barber.exception.AppException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.berber_co.Validations.ERROR;

@UtilityClass
public final class SecurityUtil {

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        }

        throw new AppException(ERROR, "Invalid user authentication principal");
    }

    public static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getEmail();
        }

        throw new AppException(ERROR, "Invalid user authentication principal");
    }
}
