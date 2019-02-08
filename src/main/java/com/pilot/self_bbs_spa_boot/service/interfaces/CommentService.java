package com.pilot.self_bbs_spa_boot.service.interfaces;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Comment;
import com.pilot.self_bbs_spa_boot.entity.CommentDto;

public interface CommentService {
    Iterable<Comment> commentListWithPage(int postIdx);
    void deleteByPostIdx(int postIdx);
    Comment reply(CommentDto commentDto, int postIdx);
    ResultMessage<Comment> edit(int postIdx, int commentIdx, CommentDto commentDto, String token);
    ResultMessage delete(int commentIdx, String token);
    boolean deleteAll(Iterable<Comment> userComments);
    Iterable<Comment> findByCommentWriter(String email);
}
