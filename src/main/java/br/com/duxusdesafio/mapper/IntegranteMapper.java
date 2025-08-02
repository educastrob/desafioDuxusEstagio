package br.com.duxusdesafio.mapper;

import br.com.duxusdesafio.controller.dto.IntegranteDTO;
import br.com.duxusdesafio.model.Integrante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IntegranteMapper {
    
    public Integrante toEntity(IntegranteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Integrante integrante = new Integrante();
        integrante.setId(dto.getId() != null ? dto.getId() : 0);
        integrante.setFranquia(dto.getFranquia());
        integrante.setNome(dto.getNome());
        integrante.setFuncao(dto.getFuncao());
        return integrante;
    }
    
    public IntegranteDTO toDto(Integrante entity) {
        if (entity == null) {
            return null;
        }
        
        IntegranteDTO dto = new IntegranteDTO();
        dto.setId(entity.getId());
        dto.setFranquia(entity.getFranquia());
        dto.setNome(entity.getNome());
        dto.setFuncao(entity.getFuncao());
        return dto;
    }
    
    public List<IntegranteDTO> toDtoList(List<Integrante> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}