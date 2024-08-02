package com.example.app_proposta.dto.response;

public record PropostaResponseDto(Long id,
                                  String nome, String sobrenome, String telefone, String cpf,
                                  Double renda, String valorSolicitadoFmt,
                                  int prazoPagamento) {}
