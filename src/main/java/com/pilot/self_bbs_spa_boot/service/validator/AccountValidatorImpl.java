package com.pilot.self_bbs_spa_boot.service.validator;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Account;
import com.pilot.self_bbs_spa_boot.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AccountValidatorImpl implements AccountValidator  {
    private static final String emailRegExp ="[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\\.[_0-9a-zA-Z-]+){1,2}$";
    private Pattern pattern = Pattern.compile(emailRegExp);

    private AccountRepository accountRepository;
    public AccountValidatorImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public ResultMessage validate(Account account) {
        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            return new ResultMessage("fail", "이메일을 비워둘 수 없습니다", true);
        } else {
            Matcher matcher = pattern.matcher(account.getEmail());
            if (!matcher.matches()) {
                return new ResultMessage("fail", "이메일의 형식이 올바르지 않습니다", true);
            }
        }
        if( duplicatedCheck(account.getEmail()) ){
            return new ResultMessage("fail", "사용 중인 이메일 입니다", true);
        }

        return new ResultMessage("success", "사용 가능한 이메일입니다", false);
    }

    @Override
    public boolean duplicatedCheck(String email){
        Optional<Account> checkAccount = accountRepository.findById(email);
        if(checkAccount.isPresent()){
            return true;
        }
        return false;
    }


    @Override
    public ResultMessage userExistencyCheck(String email) {
        Optional<Account> checkAccount = accountRepository.findById(email);
        if(!checkAccount.isPresent()){
            return new ResultMessage("fail", "존재하지 않는 계정입니다", true);
        }
        return new ResultMessage("success", email, checkAccount.get(), false);
    }


    @Override
    public ResultMessage loginPasswordCheck(Account account, String encryptedPassword) {
        if(!encryptedPassword.equals(account.getPassword())){
            return new ResultMessage("fail", "비밀번호가 일치하지 않습니다", true);
        }
        return new ResultMessage("success", "로그인 성공", account, false);

    }
}
