package com.pilot.self_bbs_spa_boot.service.validator;


import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Article;

public interface WriterCheckValidator {

    boolean checkWriter(String writer, String token);
    ResultMessage getResultMessageByWriterCheck(int elementIdx, Article article, String token);

}
