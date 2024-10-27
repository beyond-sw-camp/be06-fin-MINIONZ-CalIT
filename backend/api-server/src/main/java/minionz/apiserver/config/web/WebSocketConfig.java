package minionz.apiserver.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/note")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/chatbot")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 커서 위치 경로 추가
        registry.enableSimpleBroker("/sub", "/topic/note", "/topic/note/cursor", "/subUser");
        registry.setApplicationDestinationPrefixes("/pub", "/app/note", "/app/cursor", "/pubBot");
    }
}
