package br.com.duxusdesafio.service;

import br.com.duxusdesafio.exception.ResourceNotFoundException;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.ComposicaoTimeRepository;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeService {
    
    private final TimeRepository timeRepository;
    private final IntegranteRepository integranteRepository;
    private final ComposicaoTimeRepository composicaoTimeRepository;
    
    @Transactional(readOnly = true)
    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Time buscarPorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time", "id", id));
    }
    
    public Time salvar(Time time, List<Long> integrantesIds) {
        Time timeSalvo = timeRepository.save(time);

        if (integrantesIds != null && !integrantesIds.isEmpty()) {
            List<ComposicaoTime> composicoes = new ArrayList<>();
            
            for (Long integranteId : integrantesIds) {
                Optional<Integrante> integranteOpt = integranteRepository.findById(integranteId);
                if (integranteOpt.isPresent()) {
                    ComposicaoTime composicao = new ComposicaoTime(timeSalvo, integranteOpt.get());
                    composicoes.add(composicao);
                }
            }

            composicaoTimeRepository.saveAll(composicoes);
        }
        
        return timeSalvo;
    }
    
    public void deletar(Long id) {
        if (!timeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Time", "id", id);
        }
        timeRepository.deleteById(id);
    }
} 