package com.pilot.self_bbs_spa_boot.entity;


import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Auth;
import com.pilot.self_bbs_spa_boot.entity.enumForEntity.Status;
import lombok.Data;

@Data
public class AccountDto {

    private String email;
    private String password;
    private String name;

    private Auth auth= Auth.USER;
    private Status status = Status.ENABLE;

}
