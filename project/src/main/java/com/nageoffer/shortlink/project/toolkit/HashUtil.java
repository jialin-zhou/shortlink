package com.nageoffer.shortlink.project.toolkit;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * HASH 工具类
 */
public class HashUtil {
    private static final char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final int SIZE = CHARS.length;

    /**
     * 将十进制数转换为62进制表示的字符串。
     * 使用一个预定义的字符集（CHARS），包含0-9，A-Z，a-z共62个字符。
     *
     * @param num 需要转换的十进制数。
     * @return 转换后的62进制字符串。
     */
    private static String convertDecToBase62(long num) {
        StringBuilder sb = new StringBuilder();
        // 通过取余数的方式，从低位到高位逐步构建62进制字符串
        while (num > 0) {
            int i = (int) (num % SIZE); // 取余数
            sb.append(CHARS[i]); // 将余数对应的字符追加到字符串中
            num /= SIZE; // 更新num为商，继续下一轮迭代
        }
        // 字符串逆序后返回
        return sb.reverse().toString();
    }


    /**
     * 将字符串转换为基于Base62编码的字符串。
     * 此方法首先使用MurmurHash算法对输入字符串进行哈希，然后将得到的哈希值转换为Base62编码。
     *
     * @param str 需要进行Base62编码的字符串。
     * @return 返回基于Base62编码的字符串。
     */
    public static String hashToBase62(String str) {
        // 使用MurmurHash32算法获取字符串的哈希值
        int i = MurmurHash.hash32(str);
        // 将哈希值转换为Long类型，并确保它是正数
        long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
        // 将转换后的正整数转换为Base62编码字符串
        return convertDecToBase62(num);
    }

}
