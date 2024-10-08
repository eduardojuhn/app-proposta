package com.example.app_proposta.service;

import com.example.app_proposta.dto.request.PropostaRequestDto;
import com.example.app_proposta.dto.response.PropostaResponseDto;
import com.example.app_proposta.entity.Proposta;
import com.example.app_proposta.mapper.PropostaMapper;
import com.example.app_proposta.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);
        notificacaoService.notificar(proposta, exchange);
        return PropostaMapper.INSTANCE.convertEntitytoDto(proposta);
    }

    public void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException e) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListToDto(propostaRepository.findAll());
    }
}
