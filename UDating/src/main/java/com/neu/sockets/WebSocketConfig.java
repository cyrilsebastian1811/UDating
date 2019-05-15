package com.neu.sockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.*;

import java.util.ArrayList;
import java.util.List;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue" ,"/user");
	    config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new ChannelInterceptorAdapter() {

			@Override
			public Message<?> preSend(Message<?> data, MessageChannel channel) {
				System.out.println("------------------------------");
				System.out.println("------------------------------");
				System.out.println("------------------------------");
				System.out.println("------------------------------");
				System.out.println("------------------------------");
				StompHeaderAccessor accessor =
						MessageHeaderAccessor.getAccessor(data, StompHeaderAccessor.class);

				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String user = accessor.getFirstNativeHeader("user");
					if (!StringUtils.isEmpty(user)) {
						List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
						authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
						Authentication auth = new UsernamePasswordAuthenticationToken(user, user, authorities);
						SecurityContextHolder.getContext().setAuthentication(auth);
						accessor.setUser(auth);
					}
				}
				
				System.out.println("***********************");
				System.out.println("***********************");
				System.out.println("***********************");
				System.out.println("***********************");
				System.out.println("***********************");
				return data;
			}
			
		});
}
}
