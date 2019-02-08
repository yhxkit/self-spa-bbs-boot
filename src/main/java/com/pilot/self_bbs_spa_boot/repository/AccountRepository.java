package com.pilot.self_bbs_spa_boot.repository;

import com.pilot.self_bbs_spa_boot.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
    Account findByEmail(String email);
    Page<Account> findAll(Pageable pageable);
    Iterable<Account> findAllByEmailContainingOrderByAccountCreatedTimeDesc(String email);
    Account findByPostsPostIdx(int postIdx);
    Account findByCommentsCommentIdx(int commentIdx);


    //아래로 페이징 관련
//    Iterable<Account> findByEmailIgnoreCaseContainingOrderByAccountCreatedTimeDesc(String email);// , PageRequest pageRequest 오류남
//    Page<Account> findByEmailIgnoreCaseContainingOrderByAccountCreatedTimeDesc(String email, PageRequest pageRequest);
//    Page<Account> findByEmailIgnoreCaseContaining(String k, PageRequest p);
//    Page<Account> findAllByEmailContaining(String email, PageRequest p);

}
