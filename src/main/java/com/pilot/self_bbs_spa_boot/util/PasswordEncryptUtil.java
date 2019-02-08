package com.pilot.self_bbs_spa_boot.util;


public interface PasswordEncryptUtil {
    String encrypt(String str);
    default String decrypt(String str){ throw new RuntimeException(); }

}
