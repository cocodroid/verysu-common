package com.verysu.basic.util;

import java.text.DecimalFormat;

/**
 * 数字工具
 *
 * @author Cocodroid
 * @create 2017-09-10 0:39
 */
public class DigitUtil {

//    private final static long Hundred_Thousand = 100000;
    private final static long Ten_Thousand = 10000;
    private final static long Thousand = 1000;

    /**
     * 数字转换为文字
     * #.#
     * k、w
     * @param number
     * @return
     */
    public static String digitToDisplay(int number){
        String display = ""+number;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
//        if(number > Hundred_Thousand){
//            display = decimalFormat.format(number*1.0/Hundred_Thousand)+"w+";
//        }else
        if(number > Ten_Thousand){
            display = decimalFormat.format(number*1.0/Ten_Thousand)+"w+";
        }else if(number > Thousand){
            display = decimalFormat.format(number*1.0/Thousand)+"k+";
        }
        return display;
    }

    public static void main(String[] args) {
        System.out.println(digitToDisplay(800));
        System.out.println(digitToDisplay(1010));
        System.out.println(digitToDisplay(1055));
        System.out.println(digitToDisplay(1800));
        System.out.println(digitToDisplay(2200));
        System.out.println(digitToDisplay(23100));
        System.out.println(digitToDisplay(132421));
        System.out.println(digitToDisplay(211111));
        System.out.println(digitToDisplay(678907));
        System.out.println(digitToDisplay(8888776));
        System.out.println(digitToDisplay(89432525));
        System.out.println(digitToDisplay(894325025));
    }
}
