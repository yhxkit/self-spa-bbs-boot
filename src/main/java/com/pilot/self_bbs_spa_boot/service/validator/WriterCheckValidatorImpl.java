package com.pilot.self_bbs_spa_boot.service.validator;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Account;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Article;
import com.pilot.self_bbs_spa_boot.repository.AccountRepository;
import com.pilot.self_bbs_spa_boot.service.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WriterCheckValidatorImpl implements WriterCheckValidator {

    private AccountRepository accountRepository;
    private JWT jwt;

    public WriterCheckValidatorImpl(JWT jwt, AccountRepository accountRepository) {
        this.jwt = jwt;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean checkWriter(String writer, String token) {
        Map tokenMap = jwt.parseToken(token);
        String loginUserEmail = (String) tokenMap.get("subject");
        if (writer.equals(loginUserEmail)) {
            return true;
        }
        log.debug("작성자 불일치>> 데이터 작성자 : " + writer + " / 데이터를 변경하려는 로그인 유저 : " + loginUserEmail);
        return false;
    }


    @Override
    public ResultMessage getResultMessageByWriterCheck(int elementIdx, Article article, String token) {
        ResultMessage resultMessage;
        Account writerAccount;

        if (article.equals(Article.COMMENT)) {
            writerAccount = accountRepository.findByCommentsCommentIdx(elementIdx);
        } else {
            writerAccount = accountRepository.findByPostsPostIdx(elementIdx);
        }
        String writer = writerAccount.getEmail();

        if (checkWriter(writer, token)) {
            resultMessage = new ResultMessage("success", "작성자 일치", false);
        } else {
            resultMessage = new ResultMessage("fail", "작성자가 아닙니다", true);
        }

        return resultMessage;
    }

}
