package com.nutrili.Utils;

public class GenericMethods {
    public static <T> T nvl(T a, T b) {
        return (a == null)?b:a;
    }
}
