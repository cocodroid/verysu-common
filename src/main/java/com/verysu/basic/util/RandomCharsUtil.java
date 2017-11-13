package com.verysu.basic.util;

import com.google.common.base.Preconditions;

import java.util.Random;

/**
 * 随机字符工具
 *
 * @author Cocodroid
 * @create 2017-11-13 15:53
 */
public class RandomCharsUtil {
    final static char[] DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    final static char[] LOWERCASE_CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'n', 'm',
                                            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    final static char[] UPPERCASE_CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                                            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 随机字符
     * @param n 个数
     * @return
     */
    public static String rndChars(int n){
        int lowercaseLen = LOWERCASE_CHARS.length;
        int uppercaseLen = UPPERCASE_CHARS.length;
        int digitLen = DIGIT_CHARS.length;
        int totalLen = lowercaseLen + uppercaseLen + digitLen;
        Preconditions.checkArgument(n > 0, "n should positive number ");
        Preconditions.checkArgument(n <= totalLen, "n should not greater than (lowercaseLen+uppercaseLen)="+totalLen);
        char[] letters = new char[totalLen];
        System.arraycopy(LOWERCASE_CHARS, 0, letters, 0, lowercaseLen);
        System.arraycopy(UPPERCASE_CHARS, 0, letters, lowercaseLen, uppercaseLen);
        System.arraycopy(DIGIT_CHARS, 0, letters, lowercaseLen+uppercaseLen, digitLen);
        return rndChars(letters, n);
    }

    /**
     * 随机字母
     * @param n 个数
     * @return
     */
    public static String rndLetter(int n){
        int lowercaseLen = LOWERCASE_CHARS.length;
        int uppercaseLen = UPPERCASE_CHARS.length;
        int totalLen = lowercaseLen + uppercaseLen;
        Preconditions.checkArgument(n > 0, "n should positive number ");
        Preconditions.checkArgument(n <= totalLen, "n should not greater than (lowercaseLen+uppercaseLen)="+totalLen);
        char[] letters = new char[totalLen];
        System.arraycopy(LOWERCASE_CHARS, 0, letters, 0, lowercaseLen);
        System.arraycopy(UPPERCASE_CHARS, 0, letters, lowercaseLen, uppercaseLen);
        return rndChars(letters, n);
    }

    /**
     * 随机小写字母
     * @param n 个数
     * @return
     */
    public static String rndLowercase(int n){
        int lowercaseLen = LOWERCASE_CHARS.length;
        Preconditions.checkArgument(n > 0, "n should positive number ");
        Preconditions.checkArgument(n <= lowercaseLen, "n should not greater than lowercaseLen="+lowercaseLen);
        return rndChars(LOWERCASE_CHARS, n);
    }

    /**
     * 随机大写字母
     * @param n 个数
     * @return
     */
    public static String rndUppercase(int n){
        int lowercaseLen = UPPERCASE_CHARS.length;
        Preconditions.checkArgument(n > 0, "n should positive number ");
        Preconditions.checkArgument(n <= lowercaseLen, "n should not greater than lowercaseLen="+lowercaseLen);
        return rndChars(UPPERCASE_CHARS, n);
    }

    /**
     * 随机数字
     * @param n 个数
     * @return
     */
    public static String rndDigit(int n){
        int lowercaseLen = DIGIT_CHARS.length;
        Preconditions.checkArgument(n > 0, "n should positive number ");
        Preconditions.checkArgument(n <= lowercaseLen, "n should not greater than lowercaseLen="+lowercaseLen);
        return rndChars(DIGIT_CHARS, n);
    }


    public static String rndChars(char[] chars, int n){
//        Preconditions.checkArgument(n > 0, "n should positive number ");
//        Preconditions.checkArgument(n <= chars.length, "n should not greater than "+chars.length);
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            int index = rnd.nextInt(chars.length);
            sb.append(chars[index]);
        }
        rnd = null;
        return sb.toString();
    }

    public static void main(String[] args) {
        char[] letters = new char[LOWERCASE_CHARS.length + UPPERCASE_CHARS.length];
        System.arraycopy(LOWERCASE_CHARS, 0, letters, 0, LOWERCASE_CHARS.length);
        System.arraycopy(UPPERCASE_CHARS, 0, letters, LOWERCASE_CHARS.length, UPPERCASE_CHARS.length);
        System.out.println(letters);

//        System.out.println(rndLetter(-5));
        System.out.println(rndLetter(5));
        System.out.println(rndChars(5));
        System.out.println(rndDigit(5));
        System.out.println(rndLowercase(5));
        System.out.println(rndUppercase(5));
    }
}
