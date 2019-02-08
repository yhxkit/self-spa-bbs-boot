package com.pilot.self_bbs_spa_boot.service;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Account;
import com.pilot.self_bbs_spa_boot.entity.Comment;
import com.pilot.self_bbs_spa_boot.entity.CommentDto;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Article;
import com.pilot.self_bbs_spa_boot.repository.AccountRepository;
import com.pilot.self_bbs_spa_boot.repository.CommentRepository;
import com.pilot.self_bbs_spa_boot.repository.PostRepository;
import com.pilot.self_bbs_spa_boot.service.interfaces.CommentService;
import com.pilot.self_bbs_spa_boot.service.validator.ArticleValidator;
import com.pilot.self_bbs_spa_boot.service.validator.WriterCheckValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private AccountRepository accountRepository;
    private PostRepository postRepository;
    
    private WriterCheckValidator writerCheckValidator;
    private ArticleValidator articleValidator;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, AccountRepository accountRepository, WriterCheckValidator writerCheckValidator, ArticleValidator articleValidator) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.writerCheckValidator = writerCheckValidator;
        this.articleValidator = articleValidator;
    }

    @Override
    public Iterable<Comment> commentListWithPage(int postIdx){
        return commentRepository.findByBelongingPostPostIdxOrderByCommentIdxDesc(postIdx);
    }

    @Override
    public void deleteByPostIdx(int postIdx){
        Iterable<Comment> deletingComments = commentRepository.findByBelongingPostPostIdx(postIdx);
        commentRepository.deleteAll(deletingComments);
    }

    @Override
    public boolean deleteAll(Iterable<Comment> userComments) {
        commentRepository.deleteAll(userComments);
        return true;
    }

    @Override
    public Iterable<Comment> findByCommentWriter(String email) {
        return commentRepository.findByCommentWriter_Email(email);
    }

    @Override
    public Comment reply(CommentDto commentDto, int postIdx) {
    		
    	Comment comment = new Comment();
    	comment.setBelongingPost(postRepository.findByPostIdx(postIdx));
    	
    	Account writer = accountRepository.findByEmail(commentDto.getCommentWriterEmail());
    	comment.setCommentWriter(writer);
    	comment.setCommentContent(commentDto.getCommentContent());
    	
       return commentRepository.save(comment);
    }


    @Override
    public ResultMessage<Comment> edit(int postIdx, int commentIdx, CommentDto commentDto, String token){

        Comment origin = commentRepository.findByCommentIdx(commentIdx);
        ResultMessage resultMessage = articleValidator.articleExistencyCheck(origin);
        if(resultMessage.isImmediateReturn()){
            return  resultMessage;
        }

        resultMessage = writerCheckValidator.getResultMessageByWriterCheck(commentIdx, Article.COMMENT, token);
        if(resultMessage.isImmediateReturn()){
            return resultMessage;
        }

    	origin.setCommentContent(commentDto.getCommentContent());
    	resultMessage.setData(commentRepository.save(origin));
        return resultMessage;
    }


    @Override
    public ResultMessage delete(int commentIdx, String token) {

        Comment origin = commentRepository.findByCommentIdx(commentIdx);
        ResultMessage resultMessage = articleValidator.articleExistencyCheck(origin);
        if (resultMessage.isImmediateReturn()) {
            return resultMessage;
        }

        resultMessage = writerCheckValidator.getResultMessageByWriterCheck(commentIdx, Article.COMMENT, token);
        if (resultMessage.isImmediateReturn()) {
            return resultMessage;
        }
        commentRepository.delete(origin);
        return resultMessage;

    }



}
