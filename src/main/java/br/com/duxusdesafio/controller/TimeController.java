package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.controller.dto.TimeDTO;
import br.com.duxusdesafio.mapper.TimeMapper;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/times")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TimeController {
    
    private final TimeService timeService;
    private final TimeMapper timeMapper;
    
    @GetMapping
    public ResponseEntity<List<TimeDTO>> listarTodos() {
        List<Time> times = timeService.listarTodos();
        List<TimeDTO> timesDTO = timeMapper.toDtoList(times);
        
        return ResponseEntity.ok(timesDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TimeDTO> buscarPorId(@PathVariable Long id) {
        Time time = timeService.buscarPorId(id);
        return ResponseEntity.ok(timeMapper.toDto(time));
    }
    
    @PostMapping
    public ResponseEntity<TimeDTO> criar(@Valid @RequestBody TimeDTO timeDTO) {
        Time time = timeMapper.toEntity(timeDTO);
        Time timeSalvo = timeService.salvar(time, timeDTO.getIntegrantesIds());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timeMapper.toDto(timeSalvo));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TimeDTO> atualizar(@PathVariable Long id, 
                                           @Valid @RequestBody TimeDTO timeDTO) {
        timeService.buscarPorId(id);
        
        timeDTO.setId(id);
        Time time = timeMapper.toEntity(timeDTO);
        Time timeSalvo = timeService.salvar(time, timeDTO.getIntegrantesIds());
        
        return ResponseEntity.ok(timeMapper.toDto(timeSalvo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        timeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
