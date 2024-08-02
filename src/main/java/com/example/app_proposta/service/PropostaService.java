package com.example.app_proposta.service;

import com.example.app_proposta.dto.request.PropostaRequestDto;
import com.example.app_proposta.dto.response.PropostaResponseDto;
import com.example.app_proposta.entity.Proposta;
import com.example.app_proposta.mapper.PropostaMapper;
import com.example.app_proposta.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);
        propostaRepository.save(proposta);
        return PropostaMapper.INSTANCE.convertEntitytoDto(proposta);
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListToDto(propostaRepository.findAll());
    }
}
