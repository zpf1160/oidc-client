package com.auth0.example.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
public class LogoutController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/signout")
    protected String signout(final HttpServletRequest req) {
    	System.out.println("==========================================================");
    	System.out.println("==========================================================");
    	System.out.println("==========================================================");
    	System.out.println("==========================================================");
        logger.debug("Performing logout");
        invalidateSession(req);
        return "redirect:" + req.getContextPath() + "/login";
    }
    
    @RequestMapping(value = "/signout-remote")
    protected String signoutRemote(final HttpServletRequest req) {

    	logger.debug("Performing logout");
        invalidateSession(req);
        String id_token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        SecurityContextHolder.clearContext();
        Cookie[] cookies = req.getCookies();
        
        for (Cookie co : cookies) {
        	System.out.println(co.getName());
        	
        	if ("JSESSIONID".equals(co.getName().trim())) {
        		co.setMaxAge(0);
        	}
        	
        }
        
        return "redirect:" + "http://192.168.0.158:8091/connect/endsession?get_logout_redirect_uri=http://192.168.0.174:3000/sigout"
        + "&id_token_hint=" + id_token;
        
//        req.getContextPath() + "/signout";
    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

}
