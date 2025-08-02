package br.com.duxusdesafio.service;

import br.com.duxusdesafio.exception.ResourceNotFoundException;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IntegranteService {
    
    private final IntegranteRepository integranteRepository;
    
    @Transactional(readOnly = true)
    public List<Integrante> listarTodos() {
        return integranteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Integrante buscarPorId(Long id) {
        return integranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Integrante", "id", id));
    }
    
    public Integrante salvar(Integrante integrante) {
        return integranteRepository.save(integrante);
    }
    
    public void deletar(Long id) {
        if (!integranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Integrante", "id", id);
        }
        integranteRepository.deleteById(id);
    }
} 