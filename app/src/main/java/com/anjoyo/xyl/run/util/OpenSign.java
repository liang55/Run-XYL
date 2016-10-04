package com.anjoyo.xyl.run.util;
/**
 * @author xyl
 */

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OpenSign {
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String UTF_8 = "UTF-8";

    public static String encodeUrl(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, UTF_8).replace("+", "%20").replace("*", "%2A");
    }
    public static String makeSig(String str, String str2, HashMap<String, String> hashMap) throws Exception {
//        POST++++str2=/sport/send_phone_verify_code++++str3=201505yuedongxiaoyue&
        String key = "201505yuedongxiaoyue&";
        return makeSig( str,  str2, hashMap, key);
    }
    public static String makeSig(String str, String str2, HashMap<String, String> hashMap,String key) throws Exception {
//        POST++++str2=/sport/send_phone_verify_code++++str3=201505yuedongxiaoyue&
        Mac instance = Mac.getInstance(HMAC_ALGORITHM);
        instance.init(new SecretKeySpec(key.getBytes(UTF_8), instance.getAlgorithm()));
        return Base64.encodeToString(instance.doFinal(makeSource(str, str2, hashMap).getBytes(UTF_8)), Base64.NO_WRAP);
    }

    public static String makeSource(String str, String str2, HashMap<String, String> hashMap) throws UnsupportedEncodingException {
        Object[] toArray = hashMap.keySet().toArray();
        Arrays.sort(toArray);
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(str.toUpperCase()).append("&").append(encodeUrl(str2)).append("&");
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < toArray.length; i++) {
            stringBuilder2.append(toArray[i]).append("=").append(hashMap.get(toArray[i]));
            if (i != toArray.length - 1) {
                stringBuilder2.append("&");
            }
        }
        stringBuilder.append(encodeUrl(stringBuilder2.toString()));
        return stringBuilder.toString();
    }
}