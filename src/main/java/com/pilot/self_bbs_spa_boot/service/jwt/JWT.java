package com.pilot.self_bbs_spa_boot.service.jwt;

import com.pilot.self_bbs_spa_boot.entity.Account;

import java.util.Map;

public interface JWT {
	String getJwt(Account account);
	Map parseToken(String token);
}
