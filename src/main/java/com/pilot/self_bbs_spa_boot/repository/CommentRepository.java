package com.pilot.self_bbs_spa_boot.repository;

import com.pilot.self_bbs_spa_boot.entity.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> { // CrudRepository<Comment, Integer> {
    Iterable<Comment> findByBelongingPostPostIdxOrderByCommentIdxDesc(int postIdx);
    Iterable<Comment> findByBelongingPostPostIdx(int postIdx);
    Comment findByCommentIdx(int commentIdx);
    Iterable<Comment> findByCommentWriter_Email(String email);

    // Iterable<Comment> findByBelongingPostPostIdx(int postIdx, PageRequest pageable);
    //Iterable<Comment> findByBelongingPostPostIdxOrderByCommentIdxDesc(int postIdx, PageRequest pageRequest); //오류남..
}
