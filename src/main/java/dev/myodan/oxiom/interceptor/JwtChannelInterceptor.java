package dev.myodan.oxiom.interceptor;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.notNull(accessor, "No StompHeaderAccessor found.");

        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        String authHeader = accessor.getFirstNativeHeader("Authorization");

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return message;
        }

        String token = authHeader.substring(7);
        User user = jwtUtil.validateToken(token);

        if (user == null) {
            return message;
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, token, userPrincipal.getAuthorities());

        accessor.setUser(authentication);

        return message;
    }

}
