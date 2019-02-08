package com.pilot.self_bbs_spa_boot.service.validator;


import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Account;

public interface AccountValidator {
    ResultMessage validate(Account account);
    boolean duplicatedCheck(String email);

    ResultMessage userExistencyCheck(String email);
    ResultMessage loginPasswordCheck(Account account, String password);



}

