package com.blesk.accountservice.Utilitie;

public class Tools {

    public static <T> T getNotNull(T a, T b) {
        return b != null && a != null && !a.equals(b) ? a : b;
    }
}