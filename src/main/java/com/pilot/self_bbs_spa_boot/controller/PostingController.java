package com.pilot.self_bbs_spa_boot.controller;

import com.pilot.self_bbs_spa_boot.controller.interceptor.CustomAnnotation;
import com.pilot.self_bbs_spa_boot.controller.interceptor.EnumForCustomInterceptor;
import com.pilot.self_bbs_spa_boot.controller.responseUtil.EntityForResponse;
import com.pilot.self_bbs_spa_boot.entity.CommentDto;
import com.pilot.self_bbs_spa_boot.entity.Post;
import com.pilot.self_bbs_spa_boot.entity.PostDto;
import com.pilot.self_bbs_spa_boot.entity.Searching;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.PostCategory;
import com.pilot.self_bbs_spa_boot.service.interfaces.CommentService;
import com.pilot.self_bbs_spa_boot.service.interfaces.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class PostingController {

    private CommentService commentService;
    private PostService postService;
    private EntityForResponse responseEntity;


    public PostingController(CommentService commentService, PostService postService, EntityForResponse responseEntity) {
        this.commentService = commentService;
        this.postService = postService;
        this.responseEntity = responseEntity;
    }

    

    @GetMapping("/bbs")
    public Page<Post> getAllPosts(@RequestParam(value="page", required = false, defaultValue = "1") int page, @RequestParam(value="elementsNumberForOnePage") int elementsNumberForOnePage){
        log.debug("bbs 입장");
        Page<Post> posts = postService.bbsWithPage(page-1, elementsNumberForOnePage);
        return posts;
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @PostMapping("/bbs/write")
    public ResponseEntity<?> write(@RequestBody PostDto postDto){
        log.debug("포스트 작성 "+ postDto.getTitle()+" "+postDto.getPostContent());
        return responseEntity.get(postService.write(postDto));
    }
    
    @GetMapping("/bbs/{1}")
    public ResponseEntity<?> detail(@PathVariable("1") int postIdx){
        log.debug("상세보기 "+postIdx);
        List<Object> postDetailAndCommentList = new ArrayList(); 
        postDetailAndCommentList.add(postService.detail(postIdx));
        postDetailAndCommentList.add( commentService.commentListWithPage(postIdx));
        return responseEntity.get(postDetailAndCommentList);
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @PutMapping("/bbs/{1}")
    public ResponseEntity<?> editPost(@RequestBody PostDto postDto, HttpServletRequest request, @PathVariable("1") int postIdx){
        log.debug("포스트 수정 "+postDto.getTitle()+" : "+postDto.getPostContent()+ "("+postDto.getPostIdx()+")");
        String token = request.getHeader("token");
        return responseEntity.get(postService.edit(postIdx, postDto, token));
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @DeleteMapping("/bbs/{1}")
    public ResponseEntity<?> deletePost(@PathVariable("1") int postIdx, HttpServletRequest request){
        log.debug("포스트 삭제 "+postIdx);
        String token = request.getHeader("token");
        return responseEntity.get(postService.delete(postIdx, token));
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @PostMapping("/bbs/{1}/comment")
    public ResponseEntity<?> reply(@RequestBody CommentDto commentDto , @PathVariable("1") int postIdx, HttpServletRequest request ){
        log.debug(postIdx+"번 포스트의 코멘트 작성 "+commentDto.getCommentContent() + ":" + commentDto.getCommentWriterEmail());
        return responseEntity.get(commentService.reply(commentDto, postIdx));
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @PutMapping("/bbs/{1}/{2}")
    public ResponseEntity<?> editComment(@RequestBody CommentDto commentDto, @PathVariable("1") int postIdx, @PathVariable("2") int commentIdx, HttpServletRequest request){
        log.debug(postIdx+"번 포스트의 "+commentDto.getCommentIdx()+" 코멘트 수정 "+ commentDto.getCommentContent());
        String token = request.getHeader("token");
        return responseEntity.get(commentService.edit(postIdx, commentIdx, commentDto, token));
    }

    @CustomAnnotation(EnumForCustomInterceptor.LOGIN)
    @DeleteMapping("/bbs/{1}/{2}")
    public ResponseEntity<?> deleteComment(@PathVariable("1") int postIdx, @PathVariable("2") int commentIdx, HttpServletRequest request){
        log.debug(postIdx+"번 포스트의 코멘트 삭제 "+commentIdx);
        String token = request.getHeader("token");
        return responseEntity.get(commentService.delete(commentIdx, token));

    }

    @PostMapping("/bbs/search")
    public ResponseEntity<?> searchPosts(@RequestBody Searching searching, @RequestParam(value="page", required = false, defaultValue = "1") int page, @RequestParam(value="elementsNumberForOnePage") int elementsNumberForOnePage){
    	String keyword = searching.getKeyword(); 
    	String category =searching.getCategory();
    	log.debug("검색 카테고리: "+category+", 검색 키워드: "+keyword);
    
    	Iterable<Post> foundPosts  = null;
    	//article validator에서 정의된 카테고리 이외의 값이 들어왔을때에 반환할 결과 메세지 검증을 만들어야 하나....
    	
    	if(PostCategory.TITLE.toString().equals( category )) {
    		foundPosts =postService.findPostsWithPage(keyword, page-1, elementsNumberForOnePage);
    	}else if(PostCategory.WRITER.toString().equals( category )) {
    		foundPosts =postService.findByWriter(keyword);
    	}
    	
    	
    	return responseEntity.get(foundPosts);

    }
    
    
  


}
