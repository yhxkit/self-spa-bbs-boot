package com.pilot.self_bbs_spa_boot.controller;

import com.pilot.self_bbs_spa_boot.controller.interceptor.CustomAnnotation;
import com.pilot.self_bbs_spa_boot.controller.interceptor.EnumForCustomInterceptor;
import com.pilot.self_bbs_spa_boot.controller.responseUtil.EntityForResponse;
import com.pilot.self_bbs_spa_boot.entity.Account;
import com.pilot.self_bbs_spa_boot.entity.AccountDto;
import com.pilot.self_bbs_spa_boot.entity.Searching;
import com.pilot.self_bbs_spa_boot.service.interfaces.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AdminController {

    private AccountService accountService;
    private EntityForResponse responseEntity;


    public AdminController(AccountService accountService, EntityForResponse responseEntity) {
        this.accountService = accountService;
        this.responseEntity = responseEntity;
    }

    @CustomAnnotation(EnumForCustomInterceptor.ADMIN)
    @GetMapping("/admin")
    public Page allUsers(@RequestParam(value="page", required = false, defaultValue = "1") int page, @RequestParam(value="elementsNumberForOnePage") int elementsNumberForOnePage){
        log.info("관리자 페이지 "+page);
        Page<Account> users = accountService.getAllUsers(page-1,  elementsNumberForOnePage);
        return users;
    }
    
    @CustomAnnotation(EnumForCustomInterceptor.ADMIN)
    @PostMapping("/admin/search")
    public ResponseEntity<?> findUsers(@RequestBody AccountDto accountDto, @RequestParam(value="page", required = false, defaultValue = "1") int page, @RequestParam(value="elementsNumberForOnePage") int elementsNumberForOnePage){
        String keyword =accountDto.getEmail();
        log.info("멤버 검색 "+keyword);
        Iterable<Account> users =accountService.findAccountsWithPage(keyword, page-1, elementsNumberForOnePage);
        return responseEntity.get(users);
    }

    @CustomAnnotation(EnumForCustomInterceptor.ADMIN)
    @PutMapping(value="/admin/{1}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> changeUserStatus(@RequestBody Searching searching){
        String userEmail = searching.getKeyword();
        String state = searching.getCategory();
        log.debug(userEmail+" 유저 권한/상태 변경 "+state);
        return responseEntity.get(accountService.changeUserState(userEmail, state));
    }





}
