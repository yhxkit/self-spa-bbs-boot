package com.pilot.self_bbs_spa_boot.entity.enumForEntity;

public enum Status {
    ENABLE, DISABLE;

    private Status opposite;
    public Status changeStatus(){
        ENABLE.opposite = DISABLE;
        DISABLE.opposite = ENABLE;
        return opposite;
    }

}
