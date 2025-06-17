package com.example.demo;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

public class Backend {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Database db = new Database();
    
    public String isValidURL(String url) {
        if (url == null) return "i";
        url = url.trim();
        if (url.trim().isEmpty() || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            return "i";
        }
        try {
            URI uri = URI.create(url);
            URL convertedUrl = uri.toURL();  
            if (convertedUrl.getHost() == null || convertedUrl.getHost().isEmpty()) {
                return "i";
            }
            String[] result = db.urlExists(url); 
            if (result != null && result[0].equals("true")) {
                return result[1]; 
            }
            return "v";
        } 
        catch (Exception e) {
            return "i";
        }
    }

    public String shortenURL(String longUrl) {
        db.showTable(); //remove when deploying
        String verification = isValidURL(longUrl);
        String key;
        if (verification.equals("i")) {
            return "Invalid";
        }
        else if (verification.equals("v")) {
            do {
                UUID uuid = UUID.randomUUID();
                BigInteger bigInt = new BigInteger(uuid.toString().replace("-", ""), 16);
                key = toBase62(bigInt).substring(0, 8); 
            } while (db.keyExists(key));
            db.insertValues(key, longUrl);
            return key;
        }
        else {
            System.out.println("Key already there");
            return verification;
        }
    }

    private static String toBase62(BigInteger value) {
        StringBuilder sb = new StringBuilder();
        while(value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] qR = value.divideAndRemainder(BigInteger.valueOf(62));
            sb.append(BASE62.charAt(qR[1].intValue()));
            value = qR[0];
        }
        return sb.reverse().toString();
    }  
}
