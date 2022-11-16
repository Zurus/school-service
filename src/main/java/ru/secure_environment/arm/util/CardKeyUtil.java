package ru.secure_environment.arm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CardKeyUtil {

    //Длина ключа, для протокола Wiegand-26
    private static final int W26_SIZE = 6;
    // Колчичество байт для целочисленной части ключа, в протоколе Wiegand-26
    private static final int W26_MANTISSA_SIZE = 2;
    // Колчичество байт для целочисленной части ключа, в протоколе Wiegand-58
    //todo: Добавить новое число!
    private static final int W58_MANTISSA_SIZE = 2;

    public static String toDecString(String hex) {
        int splitter = hex.length() == W26_SIZE ? W26_MANTISSA_SIZE : W58_MANTISSA_SIZE;
        String mantissa = String.format("%03d", Integer.valueOf(hex.substring(0, splitter), 16));
        String exponent = Integer.valueOf(hex.substring(splitter), 16).toString();
        return mantissa + "," + exponent;
    }

    public static String toHexString(String dec) {
        String[] splitString = dec.split("[.,]");
        String mantissa = splitString[0];
        String exponent = splitString[1];

        return Integer.toHexString(Integer.valueOf(mantissa)).toUpperCase()
                + Integer.toHexString(Integer.valueOf(exponent)).toUpperCase();
    }
}
