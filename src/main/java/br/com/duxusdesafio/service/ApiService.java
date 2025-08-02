package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * OBS ao candidato: PREFERENCIALMENTE, NÃO ALTERE AS ASSINATURAS DOS MÉTODOS!
 * Trabalhe com a proposta pura.
 *
 * @author carlosau
 */
@Service
@RequiredArgsConstructor
public class ApiService {

    /**
     * Vai retornar um Time, com a composição do time daquela data
     */
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes) {
        return todosOsTimes.stream()
                .filter(time -> time.getData().equals(data))
                .findFirst()
                .orElse(null);
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        Map<Integrante, Long> contagemIntegrantes = contarIntegrantes(timesNoPeriodo);
        
        return encontrarMaisComum(contagemIntegrantes).orElse(null);
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);

        Map<String, Long> contagemComposicoes = timesNoPeriodo.stream()
                .map(this::criarChaveComposicao)
                .filter(key -> !key.isEmpty())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        String composicaoMaisComum = encontrarMaisComum(contagemComposicoes).orElse("");

        if (!composicaoMaisComum.isEmpty()) {
            return Arrays.asList(composicaoMaisComum.split(","));
        }
        
        return new ArrayList<>();
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        Map<String, Long> contagemFuncoes = contarFuncoes(timesNoPeriodo);
        
        return encontrarMaisComum(contagemFuncoes).orElse(null);
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        Map<String, Long> contagemFranquias = contarFranquias(timesNoPeriodo);
        
        return encontrarMaisComum(contagemFranquias).orElse(null);
    }

    /**
     * Vai retornar o número (quantidade) de Franquias dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        return contarFranquias(timesNoPeriodo);
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesNoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        return contarFuncoes(timesNoPeriodo);
    }



    // ========== MÉTODOS AUXILIARES ESPECÍFICOS DO DOMÍNIO ==========
    
    /**
     * Filtra times por período
     */
    private List<Time> filtrarTimesPorPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        return todosOsTimes.stream()
                .filter(time -> isTimeInPeriod(time.getData(), dataInicial, dataFinal))
                .collect(Collectors.toList());
    }
    
    /**
     * Verifica se uma data está dentro do período
     */
    private boolean isTimeInPeriod(LocalDate timeData, LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            return !timeData.isBefore(dataInicial) && !timeData.isAfter(dataFinal);
        } else if (dataInicial != null) {
            return !timeData.isBefore(dataInicial);
        } else if (dataFinal != null) {
            return !timeData.isAfter(dataFinal);
        }
        return true;
    }
    
    /**
     * Conta ocorrências de integrantes nos times
     */
    private Map<Integrante, Long> contarIntegrantes(List<Time> times) {
        return times.stream()
                .filter(time -> time.getComposicaoTime() != null)
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(ComposicaoTime::getIntegrante)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }
    
    /**
     * Conta ocorrências de funções nos times
     */
    private Map<String, Long> contarFuncoes(List<Time> times) {
        return times.stream()
                .filter(time -> time.getComposicaoTime() != null)
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(comp -> comp.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }
    
    /**
     * Conta ocorrências de franquias nos times
     */
    private Map<String, Long> contarFranquias(List<Time> times) {
        return times.stream()
                .filter(time -> time.getComposicaoTime() != null)
                .flatMap(time -> time.getComposicaoTime().stream())
                .map(comp -> comp.getIntegrante().getFranquia())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }
    
    /**
     * Encontra o elemento com mais ocorrências em um Map
     */
    private <T> Optional<T> encontrarMaisComum(Map<T, Long> contagem) {
        return contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
    
    /**
     * Cria uma chave única para a composição de um time
     */
    private String criarChaveComposicao(Time time) {
        if (time.getComposicaoTime() == null) {
            return "";
        }
        return time.getComposicaoTime().stream()
                .map(comp -> comp.getIntegrante().getNome())
                .sorted()
                .collect(Collectors.joining(","));
    }
}
