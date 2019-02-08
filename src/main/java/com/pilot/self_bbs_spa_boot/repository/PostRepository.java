package com.pilot.self_bbs_spa_boot.repository;

import com.pilot.self_bbs_spa_boot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {//CrudRepository<Post, Integer> {
    Post findByPostIdx(int postIdx);
    Iterable<Post> findByPostWriterEmail(String email);
    Page<Post> findAll(Pageable pageable);
    Iterable<Post> findByPostWriterEmailIgnoreCaseContainingOrderByPostIdxDesc(String keyword);
    Iterable<Post> findByTitleIgnoreCaseContainingOrderByPostIdxDesc(String keyword);

    // Iterable<Account> findByEmailIgnoreCaseContainingOrderByAccountCreatedTimeDesc(String email);// , PageRequest pageRequest 오류남
}

