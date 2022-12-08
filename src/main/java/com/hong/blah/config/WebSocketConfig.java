package com.hong.blah.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.hong.blah.handler.SocketHandler;

@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Autowired
	SocketHandler socketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler, "/chating/{roomNumber}");
	}
	
	
	// 파일전송시 handleBinaryMessage메소드말고 afterConnectionClosed메소드를 타서 죽는 현상 해결
	// 최대 버퍼 사이즈 늘려줌
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
	ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
	container.setMaxTextMessageBufferSize(500000);
	container.setMaxBinaryMessageBufferSize(500000);
	return container;
	}
}
