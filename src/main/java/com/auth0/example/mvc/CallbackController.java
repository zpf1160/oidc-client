package com.auth0.example.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.auth0.example.security.TokenAuthentication;
import com.auth0.jwt.JWT;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@SuppressWarnings("unused")
@Controller
public class CallbackController {

    @Autowired
    private AuthenticationController controller;
    private final String redirectOnFail;
    private final String redirectOnSuccess;

    public CallbackController() {
        this.redirectOnFail = "/login";
        this.redirectOnSuccess = "/portal/home";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handle(req, res);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handle(req, res);
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    protected String hello(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        return "hello";
    }
    
    
    @RequestMapping(value = "/aaaa", method = RequestMethod.GET)
    protected void aaaa(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        int ran = (int)(Math.random()*100);
//        TokenAuthenticationEntity tokenEntity = new TokenAuthenticationEntity();
//        tokenEntity.setRan(ran);
//        SecurityContextHolder.getContext().setAuthentication(tokenEntity);    
        
        SessionUtils.set(req, "aaaa", ran);
        
        System.out.println("aaaa:" + Thread.currentThread().getName() + ":name:" + ran);
    }
    
    @RequestMapping(value = "/bbbb", method = RequestMethod.GET)
    protected void bbbb(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
//    	String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	Object object = SessionUtils.get(req, "aaaa");
        System.out.println("bbbb:" + Thread.currentThread().getName() + ":name:" + object.toString());
    }
    
    public String httpGet(String url,HttpServletRequest req) throws IOException {
    	String message = "";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + SessionUtils.get(req , "access_token").toString())
                .addHeader("ContentType", "application/json")
                .build();
        okhttp3.Response response = httpClient.newCall(request).execute();
        
        
        if (!response.isSuccessful()) {
        	//检测是否请求成功，如果请求没有成功，则刷新token再一次请求，如果还是不行，说明其他问题、
        	Tokens refreshToken = controller.refreshToken(SessionUtils.get(req , "refresh_token").toString());
        	
        	if (refreshToken != null) {
        		System.out.println("刷新刷新刷新刷新tokentoken");                           
                SessionUtils.set(req, "access_token", refreshToken.getAccessToken());
                SessionUtils.set(req, "refresh_token", refreshToken.getRefreshToken());
                
                
                OkHttpClient httpClient1 = new OkHttpClient();
                Request request1 = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "Bearer " + SessionUtils.get(req , "access_token").toString())
                        .addHeader("ContentType", "application/json")
                        .build();
                okhttp3.Response response1 = httpClient1.newCall(request1).execute();
                
                message = response1.body().string();
        	}
        	
        	
        } else {
        	message = response.body().string();
        }
        
        return message; // 返回的是string 类型，json的mapper可以直接处理
    }
    
    public String httpPost(String url, String json) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Bearer", "")
                .build();
        okhttp3.Response response = httpClient.newCall(request).execute();
        
        
        
        return "";
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    protected String test(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {

    	String httpGet = httpGet("http://192.168.0.174:3010/api/private",req);
    	
    	System.out.println("返回内容:" + httpGet);
    	
    	return httpGet;
    }
    
    

    private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
        	 String redirectUri = req.getRequestURL().toString();
        	 
        	 
//        	 Long expiresIn = req.getParameter(KEY_EXPIRES_IN) == null ? null : Long.parseLong(req.getParameter(KEY_EXPIRES_IN));
//             new Tokens(req.getParameter(KEY_ACCESS_TOKEN), req.getParameter(KEY_ID_TOKEN), req.getParameter(KEY_REFRESH_TOKEN), req.getParameter(KEY_TOKEN_TYPE), expiresIn);
        	 
            Tokens tokens = controller.handle(req);
            System.out.println("access_token:" + tokens.getAccessToken());
            System.out.println("id_token:" + tokens.getIdToken());
            System.out.println("refresh_token" + tokens.getRefreshToken());
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));
            
            
            SessionUtils.set(req, "access_token", tokens.getAccessToken());
            SessionUtils.set(req, "refresh_token", tokens.getRefreshToken());
            
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);           
//            TokenAuthenticationEntity tokenEntity = new TokenAuthenticationEntity();
//            tokenEntity.setToken(tokens);
//            SecurityContextHolder.getContext().setAuthentication(tokenEntity);                   
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            res.sendRedirect(redirectOnSuccess);
        } catch (AuthenticationException | IdentityVerificationException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
    }

}
