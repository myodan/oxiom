package dev.myodan.oxiom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().permitAll()
                .simpSubscribeDestMatchers("/user/**").authenticated()
                .simpSubscribeDestMatchers("/sub/products/**").permitAll()
                .simpSubscribeDestMatchers("/sub/chat-rooms/**").authenticated()
                .simpDestMatchers("/pub/chat-rooms/**").authenticated()
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
