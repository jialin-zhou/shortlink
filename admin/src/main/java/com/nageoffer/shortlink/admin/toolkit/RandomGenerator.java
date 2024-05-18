package com.nageoffer.shortlink.admin.toolkit;

import java.security.SecureRandom;

/**
 * 分组ID随机生成器
 */
public final class RandomGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成随机分组ID
     * @return 分组ID
     */
    public static String generateRandom() {
        return generate(6);
    }


    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
