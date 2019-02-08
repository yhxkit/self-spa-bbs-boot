package com.pilot.self_bbs_spa_boot.service.validator;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Article;
import com.pilot.self_bbs_spa_boot.entity.PostDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleValidatorImpl implements ArticleValidator {

    @Override
    public <T extends Article> ResultMessage emptyCheck(T article) {


        if(article.equals(com.pilot.self_bbs_spa_boot.entity.enumForEntity.Article.POST)){
            if( ((PostDto)article).getTitle()==null || "".equals( ((PostDto)article).getTitle().trim() ) ){
                return new ResultMessage("fail", "포스트 타이틀이 비어있습니다", true);
            }
            if( ((PostDto)article).getPostContent()==null || "".equals( ((PostDto)article).getPostContent().trim() ) ){
                return new ResultMessage("fail", "포스트 내용이 비어있습니다", true);
            }
        }
        return new ResultMessage("success", "글 작성 성공", false);
    }


    @Override
    public <T extends Article> ResultMessage articleExistencyCheck(T article) {
        Optional<? extends Article> checkArticle = Optional.ofNullable(article);
        if(!checkArticle.isPresent()){
            return new ResultMessage("fail", "삭제된 글입니다", true);
        }
        return new ResultMessage("success", "글 확인", article, false);
    }
}
