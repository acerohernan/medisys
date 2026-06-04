package com.medisys.web.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Authentication authentication) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String target = RouteRedirectHelper.getRedirectFor(authentication);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode.equals(HttpStatus.NOT_FOUND.value())) {
                return "redirect:" + target;
            }
            if (statusCode.equals(HttpStatus.FORBIDDEN.value())) {
                return "redirect:/access-denied";
            }
        }

        return "redirect:" + target;
    }
}
