package com.mj.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.concurrent.CopyOnWriteArraySet;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    public static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    //线程安全-在线人数
    private static CopyOnWriteArraySet<String> webSocketSet = new CopyOnWriteArraySet<String>();
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/tdbank-portal-websocket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        // 客户端与服务器端建立连接
                        String sessionId = session.getId();
                        webSocketSet.add(sessionId);
                        logger.info("客户端接入 from " + session.getLocalAddress() +
                                " port = " + session.getRemoteAddress().getPort() +  " sessionId = " + sessionId );
                        logger.info("客户端总数: {}",webSocketSet.size());
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        // 客户端与服务器端断开连接后
                        String sessionId = session.getId();
                        webSocketSet.remove(sessionId);
                        logger.info("客户端下线 from " + session.getLocalAddress() +
                                " port = " + session.getRemoteAddress().getPort() +  " sessionId = " + sessionId );
                        logger.info("客户端总数: {}",webSocketSet.size());
                        super.afterConnectionClosed(session, closeStatus);
                    }


                };
            }
        });
        super.configureWebSocketTransport(registration);
    }

}