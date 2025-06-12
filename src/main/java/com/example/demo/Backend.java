package com.example.demo;

import java.math.BigInteger;
import java.util.UUID;

public class Backend {
    private static final String BASE116 = "అఆఇఈఉఊఋౠఌౡఎఏఐఒఓఔకఖగఘఙచఛజఝఞటఠడఢణతథదధనపఫబభమయరలవశషసహళక్షఱ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String isValidURL(String url) {
        try {
            java.net.URI uri = new java.net.URI(url); // Validates syntax + structure
            if (uri.getScheme() == null || uri.getHost() == null) {
                return "Invalid";
            }
            return shortenURL(url);
        } catch (Exception e) {
            return "Invalid";
        }
    }

    public String shortenURL(String longUrl) {
        UUID uuid = UUID.randomUUID(); 
        BigInteger bigInt = new BigInteger(uuid.toString().replace("-", ""), 16); 
        return toBase116(bigInt).substring(0, 8); 
    }

    private static String toBase116(BigInteger value) {
        StringBuilder sb = new StringBuilder();
        while(value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] qR = value.divideAndRemainder(BigInteger.valueOf(116));
            sb.append(BASE116.charAt(qR[1].intValue()));
            value = qR[0];
        }
        return sb.reverse().toString();
    }  
}
