package com.berber_co.barber.util;

import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.user.User;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.security.CustomSellerDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    public static Long getSellerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomSellerDetails sellerDetails) {
            return sellerDetails.getId();
        }

        throw new AppException(ERROR, "Invalid seller authentication principal");
    }

}
