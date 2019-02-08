package com.pilot.self_bbs_spa_boot.controller.interceptor;

import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Auth;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Status;
import com.pilot.self_bbs_spa_boot.repository.AccountRepository;
import com.pilot.self_bbs_spa_boot.service.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@Component
public class CustomInterceptor extends HandlerInterceptorAdapter {//implements HandlerInterceptor{
		
	private JWT jwt;
	private AccountRepository accountRepository;

	public CustomInterceptor(JWT jwt, AccountRepository accountRepository) {
		this.jwt = jwt;
		this.accountRepository = accountRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod hm=(HandlerMethod)handler;
		Method method=hm.getMethod(); 

		String token =request.getHeader("token");

        if(method.getDeclaringClass().isAnnotationPresent(RestController.class)){
            if(method.isAnnotationPresent(CustomAnnotation.class)){
		        if( method.getAnnotation(CustomAnnotation.class).value() == EnumForCustomInterceptor.LOGIN ) {
		            log.debug("로그인 인터셉터..");
		            return loginCheck(token, response);
		        }
		        if(EnumForCustomInterceptor.ADMIN.toString().equals(method.getAnnotation(CustomAnnotation.class).value())) {
		            log.debug("관리자 인터셉터..");
		            if(loginCheck(token, response)) {
		                return adminCheck(token, response);
		            }
		            return false;
		        }
		    }
		}
		return true; 
	}
	
	
	
	private boolean loginCheck(String token, HttpServletResponse response) throws Exception {
	    log.info("로그인 프리 핸들러 : 토큰 "+token);
	    //현재 세션에 저장된 토큰이 유효하지 않으면 처리...

        String userEmail = (String)jwt.parseToken(token).get("subject");

        if(userEmail==null) {
            log.debug("비로그인 계정의 접근");
	       response.sendError(401, "로그인하세요");
	       return false;
        }

        Optional checkExistingUser = Optional.ofNullable(accountRepository.findByEmail(userEmail));
        if(!checkExistingUser.isPresent()){
            log.debug("토큰이 만료 "+token);
	     	response.sendError(401, "세션이 만료되었습니다. 재로그인해주세요");
	     	return false;
        }


	    if(!(boolean)jwt.parseToken(token).get("validToken")){ //유효하지 않은 토큰이면
	        log.debug("토큰이 유효하지 않음 > "+token);
	     	response.sendError(401, "로그인하세요");
	     	return false;
	    }

	    if( jwt.parseToken(token).get("status").equals(Status.DISABLE.toString()) ){
	        log.debug("정지 된 회원 > "+userEmail);
	     	response.sendError(403, "계정이 정지되었습니다");
	     	return false;
	    }
	    return true;
	}

	private boolean adminCheck (String token, HttpServletResponse response) throws Exception {
    	log.debug("관리자 프리 핸들러");
        if(!jwt.parseToken(token).get("scope").equals(Auth.ADMIN.toString())){ // scope 가 ADMIN이 아니면
        	log.debug("관리자 페이지에 접근하려는 유저 "+jwt.parseToken(token).get("subject"));
        	response.sendError(402, "관리자 권한이 필요합니다");
            return false;
        }
        return true;
	}

}
