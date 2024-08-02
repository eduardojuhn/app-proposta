package com.example.app_proposta.dto.request;

public record PropostaRequestDto(String nome, String sobrenome, String telefone, String cpf,
                                 Double renda, Double valorSolicitado,
                                 int prazoPagamento){}
