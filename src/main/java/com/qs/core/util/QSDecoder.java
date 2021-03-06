package com.qs.core.util;

import com.qs.core.model.Charset;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class QSDecoder {

    public static String decode(String input) {
        try {
            if (input == null) return null;
            return URLDecoder.decode(input, Charset.UTF8.getCharset());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
