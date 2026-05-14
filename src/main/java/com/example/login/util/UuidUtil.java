package com.example.login.util;

import java.util.UUID;

/**
 * UUID 工具类
 */
public class UuidUtil {

    /**
     * 生成32位无横线 UUID
     */
    public static String get32Uuid() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }
}