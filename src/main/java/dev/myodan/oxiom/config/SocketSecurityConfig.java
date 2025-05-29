package dev.myodan.oxiom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager.Builder;

@Configuration
@EnableWebSocketSecurity
public class SocketSecurityConfig {

    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(Builder messages) {
        return messages
                .simpSubscribeDestMatchers("/sub/products").permitAll()
                .simpDestMatchers("/pub/products").permitAll()
                .anyMessage().denyAll()
                .build();
    }

}
