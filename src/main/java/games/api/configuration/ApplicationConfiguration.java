package games.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @RequestScope
    public MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
    }
}
