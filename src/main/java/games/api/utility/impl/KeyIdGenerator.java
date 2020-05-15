package games.api.utility.impl;

import games.api.utility.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class KeyIdGenerator implements IdGenerator {

    @Autowired
    private MessageDigest messageDigest;

    @Override
    public String generate(String key) {
        byte[] id = messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().encodeToString(id);
    }
}
