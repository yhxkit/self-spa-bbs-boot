package com.pilot.self_bbs_spa_boot.service.jwt;

import com.pilot.self_bbs_spa_boot.entity.Account;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class
JWTImpl implements JWT{

	String secretKey = "SECRET";
	
	@Override
    public String getJwt(Account account){
    	
    	Date date = new Date();
    	Date ex = new Date(date.getTime()+ (long)(1000*60*60*1));//로그인 시 1시간동안 유효

        String token = Jwts.builder()
        		.setSubject(account.getEmail())
        		.setHeaderParam("typ", "JWT")
        		.setIssuedAt(date)
        		.setExpiration(ex)
        		.claim("name", account.getName())
				.claim("status", account.getStatus())
        		.claim("scope", account.getAuth())
        		.signWith(SignatureAlgorithm.HS512, secretKey)
        		.compact();

     log.info("발행 토큰 "+token);
     return token;
    	
    }
    
    
    @Override
    public HashMap<String, ?> parseToken(String token) {
    	
    	//This line will throw an exception if it is not a signed JWS (as expected)
		HashMap tokenMap = new HashMap();

        boolean validToken = false;
    	try {
    	
    	Claims claims = Jwts.parser()         
           .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
           .parseClaimsJws(token).getBody();

			log.debug(claims.getSubject()); //userEmail
			log.debug(claims.get("name").toString());
			log.debug(claims.get("scope").toString());
			log.debug(claims.get("status").toString());
			log.debug(claims.getIssuedAt().toString());
			log.debug(claims.getExpiration().toString());

			tokenMap.put("subject",  claims.getSubject()); //userEmail
			tokenMap.put("name",  claims.get("name"));
			tokenMap.put("scope",  claims.get("scope"));
			tokenMap.put("status",  claims.get("status"));
			tokenMap.put("issued", claims.getIssuedAt());
			tokenMap.put("expiration",  claims.getExpiration());

			validToken=true;

    	}catch (ExpiredJwtException e) {
			log.debug("ExpiredJwtException : "+token +"\n"+e.getMessage());
		}catch(UnsupportedJwtException e) {
			log.debug("UnsupportedJwtException : "+token+"\n"+e.getMessage());
		}catch(MalformedJwtException e) {
			log.debug("MalformedJwtException : "+token+"\n"+e.getMessage());
		}catch(SignatureException e) {
			log.debug("SignatureException : "+token+"\n"+e.getMessage());
		}catch(IllegalArgumentException e) {
			log.debug("IllegalArgumentException : "+token+"\n"+e.getMessage());
		}

		tokenMap.put("validToken",  validToken);
    	
    	return tokenMap;
    }
}
