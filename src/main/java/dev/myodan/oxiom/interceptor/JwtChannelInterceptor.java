package dev.myodan.oxiom.interceptor;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
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
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return message;
    }

}
