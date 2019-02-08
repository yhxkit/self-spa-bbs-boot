package com.pilot.self_bbs_spa_boot.entity.enumForEntity;

public enum Auth {
    ADMIN, USER;

    private Auth opposite;
    public Auth changeAuth(){
        ADMIN.opposite=USER;
        USER.opposite=ADMIN;
        return opposite;
    }
}
