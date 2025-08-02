package br.com.duxusdesafio.mapper;

import br.com.duxusdesafio.controller.dto.TimeDTO;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeMapper {
    
    public Time toEntity(TimeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Time time = new Time();
        time.setId(dto.getId() != null ? dto.getId() : 0);
        time.setData(dto.getData());
        return time;
    }
    
    public TimeDTO toDto(Time entity) {
        if (entity == null) {
            return null;
        }
        
        TimeDTO dto = new TimeDTO();
        dto.setId(entity.getId());
        dto.setData(entity.getData());

        if (entity.getComposicaoTime() != null) {
            List<String> nomes = entity.getComposicaoTime().stream()
                    .map(comp -> comp.getIntegrante().getNome())
                    .collect(java.util.stream.Collectors.toList());
            dto.setIntegrantesNomes(nomes);
        }
        
        return dto;
    }
    
    public List<TimeDTO> toDtoList(List<Time> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}