package com.example.app_proposta.service;

import com.example.app_proposta.dto.response.PropostaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificacaoService {

    private final RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDto proposta, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }
}
