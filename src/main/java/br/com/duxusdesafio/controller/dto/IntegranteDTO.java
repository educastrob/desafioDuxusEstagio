package br.com.duxusdesafio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegranteDTO {
    
    private Long id;
    
    @NotNull(message = "Franquia é obrigatória")
    private String franquia;
    
    @NotNull(message = "Nome é obrigatório")
    private String nome;
    
    @NotNull(message = "Função é obrigatória")
    private String funcao;
} 