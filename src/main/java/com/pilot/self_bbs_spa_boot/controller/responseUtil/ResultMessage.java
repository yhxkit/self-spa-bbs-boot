package com.pilot.self_bbs_spa_boot.controller.responseUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultMessage<T> {
    String result;
    String message;
    T data;
    boolean immediateReturn;

    public ResultMessage(String result, String message, boolean immediateReturn){
        this.result = result;
        this.message = message;
        this.immediateReturn = immediateReturn;
    }


}
