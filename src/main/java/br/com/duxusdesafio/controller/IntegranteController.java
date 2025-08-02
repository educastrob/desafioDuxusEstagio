package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.controller.dto.IntegranteDTO;
import br.com.duxusdesafio.mapper.IntegranteMapper;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.IntegranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/integrantes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class IntegranteController {
    
    private final IntegranteService integranteService;
    private final IntegranteMapper integranteMapper;
    
    @GetMapping
    public ResponseEntity<List<IntegranteDTO>> listarTodos() {
        List<Integrante> integrantes = integranteService.listarTodos();
        List<IntegranteDTO> integrantesDTO = integranteMapper.toDtoList(integrantes);
        
        return ResponseEntity.ok(integrantesDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<IntegranteDTO> buscarPorId(@PathVariable Long id) {
        Integrante integrante = integranteService.buscarPorId(id);
        return ResponseEntity.ok(integranteMapper.toDto(integrante));
    }
    
    @PostMapping
    public ResponseEntity<IntegranteDTO> criar(@Valid @RequestBody IntegranteDTO integranteDTO) {
        Integrante integrante = integranteMapper.toEntity(integranteDTO);
        Integrante integranteSalvo = integranteService.salvar(integrante);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(integranteMapper.toDto(integranteSalvo));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<IntegranteDTO> atualizar(@PathVariable Long id, 
                                                  @Valid @RequestBody IntegranteDTO integranteDTO) {
        integranteService.buscarPorId(id);
        
        integranteDTO.setId(id);
        Integrante integrante = integranteMapper.toEntity(integranteDTO);
        Integrante integranteSalvo = integranteService.salvar(integrante);
        
        return ResponseEntity.ok(integranteMapper.toDto(integranteSalvo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        integranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
