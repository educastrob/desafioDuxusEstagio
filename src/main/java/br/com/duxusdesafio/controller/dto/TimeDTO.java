package br.com.duxusdesafio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDTO {
    
    private Long id;
    
    @NotNull(message = "Data é obrigatória")
    private LocalDate data;
    
    private List<Long> integrantesIds;

    private List<String> integrantesNomes;
}