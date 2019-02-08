package com.pilot.self_bbs_spa_boot.service.validator;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Article;

public interface ArticleValidator {
    <T extends Article> ResultMessage emptyCheck(T article);
    <T extends Article> ResultMessage articleExistencyCheck(T article);

}
