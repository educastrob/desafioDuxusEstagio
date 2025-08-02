package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.ApiService;
import br.com.duxusdesafio.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AnaliseController {
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API funcionando!");
    }
    
    @Autowired
    private ApiService apiService;
    
    @Autowired
    private TimeService timeService;
    
    @GetMapping("/time_da_data")
    public ResponseEntity<Map<String, Object>> timeDaData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        System.out.println("Endpoint /TimeDaData chamado com data: " + data);
        
        List<Time> todosOsTimes = timeService.listarTodos();
        System.out.println("Total de times encontrados: " + todosOsTimes.size());
        
        Time time = apiService.timeDaData(data, todosOsTimes);
        
        if (time == null) {
            System.out.println("Nenhum time encontrado para a data: " + data);
            return ResponseEntity.notFound().build();
        }
        
        List<String> integrantes = time.getComposicaoTime().stream()
                .map(comp -> comp.getIntegrante().getNome())
                .collect(java.util.stream.Collectors.toList());
        
        Map<String, Object> response = Map.of(
                "data", time.getData(),
                "integrantes", integrantes
        );
        
        System.out.println("Time encontrado: " + response);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/integrante_mais_usado")
    public ResponseEntity<Map<String, Object>> integranteMaisUsado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        Integrante integrante = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);
        
        if (integrante == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = Map.of(
                "id", integrante.getId(),
                "nome", integrante.getNome(),
                "franquia", integrante.getFranquia(),
                "funcao", integrante.getFuncao()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/time_mais_comum")
    public ResponseEntity<Map<String, Object>> timeMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        List<String> integrantes = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal, todosOsTimes);
        
        Map<String, Object> response = Map.of("integrantes", integrantes);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/funcao_mais_comum")
    public ResponseEntity<Map<String, String>> funcaoMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        String funcao = apiService.funcaoMaisComum(dataInicial, dataFinal, todosOsTimes);
        
        if (funcao == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, String> response = Map.of("Função", funcao);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/franquia_mais_famosa")
    public ResponseEntity<Map<String, String>> franquiaMaisFamosa(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        String franquia = apiService.franquiaMaisFamosa(dataInicial, dataFinal, todosOsTimes);
        
        if (franquia == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, String> response = Map.of("franquia", franquia);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/contagem_por_franquia")
    public ResponseEntity<Map<String, Long>> contagemPorFranquia(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        Map<String, Long> contagem = apiService.contagemPorFranquia(dataInicial, dataFinal, todosOsTimes);
        
        return ResponseEntity.ok(contagem);
    }
    
    @GetMapping("/contagem_por_funcao")
    public ResponseEntity<Map<String, Long>> contagemPorFuncao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeService.listarTodos();
        Map<String, Long> contagem = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        
        return ResponseEntity.ok(contagem);
    }
}
