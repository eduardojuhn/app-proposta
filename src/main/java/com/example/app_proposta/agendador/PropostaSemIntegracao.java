package com.example.app_proposta.agendador;

import com.example.app_proposta.entity.Proposta;
import com.example.app_proposta.repository.PropostaRepository;
import com.example.app_proposta.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;
    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostaSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchange);
                atualizarProposta(proposta);
            } catch (RuntimeException e) {
                logger.error("Exception: ", e);            }
        });
    }

    public void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);    }
}
