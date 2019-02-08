package com.pilot.self_bbs_spa_boot.service.interfaces;

import com.pilot.self_bbs_spa_boot.controller.responseUtil.ResultMessage;
import com.pilot.self_bbs_spa_boot.entity.Account;
import com.pilot.self_bbs_spa_boot.entity.AccountDto;
import org.springframework.data.domain.Page;

public interface AccountService {
	
	
	Account castAccountDtoToAccount(AccountDto accountDto);

    ResultMessage<Account> join(Account account);
    ResultMessage<Account> login(Account account);
    ResultMessage<Account> changeUserState(String userEmail, String satateCategory);
    void delete(String email);
    
    Page<Account> getAllUsers(int requiredUserListPage, int elementsNumberForOnePage);
    Iterable<Account> findAccountsWithPage(String keyword, int page, int elementsNumberForOnePage);
    
    Account findByEmail(String email);
    Account findByPostsPostIdx(int postIdx);
    Account findByCommentsCommentIdx(int commentIdx);

    Account changeStatus(Account account);
    Account changeAuth(Account account);
    ResultMessage<Account> editNameAndPassword(String email, String newName, String newPassword);


}
