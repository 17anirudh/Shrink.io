package com.example.demo;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import org.bson.Document;

public class Backend {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public String isValidURL(String url) {
    if (url == null || url.trim().isEmpty()) {
        return "Invalid";
    }
    url = url.trim();
    
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        return "Invalid";
    }

    try {
        URI uri = URI.create(url);
        URL convertedUrl = uri.toURL();
        
        if (convertedUrl.getHost() == null || convertedUrl.getHost().isEmpty()) {
            return "Invalid";
        }
        
        return shortenURL(convertedUrl.toString());
    } catch (Exception e) {
        return "Invalid";
    }
}

    public String shortenURL(String longUrl) {
        Database db = new Database();
        String key;
        while (true) {
            UUID uuid = UUID.randomUUID();
            BigInteger bigInt = new BigInteger(uuid.toString().replace("-", ""), 16);
            key = toBase62(bigInt).substring(0, 8); 
            if (!db.keyExists(key)) {
                Document doc = new Document("key", key).append("url", longUrl).append("createdAt", System.currentTimeMillis());
                db.insertDocument(doc);
                key = "localhost:5000/" + key; 
                break;
            }
        }
        return key;
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
