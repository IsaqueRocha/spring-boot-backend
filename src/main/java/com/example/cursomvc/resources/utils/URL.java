package com.example.cursomvc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList(String str) {
        return Arrays.stream(str.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static String decodeParam(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
