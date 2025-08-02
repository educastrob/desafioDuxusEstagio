package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.IntegranteService;
import br.com.duxusdesafio.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
public class WebController {
    
    @Autowired
    private IntegranteService integranteService;
    
    @Autowired
    private TimeService timeService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/integrante")
    public String integrante() {
        return "integrante";
    }
    
    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/analises")
    public String analises() {
        return "analises";
    }
    
    @PostMapping("/api/popular_dados")
    @ResponseBody
    public String popularDados() {
        try {
            Integrante integrante1 = new Integrante();
            integrante1.setFranquia("Apex Legends");
            integrante1.setNome("Wraith");
            integrante1.setFuncao("Assault");
            integranteService.salvar(integrante1);
            
            Integrante integrante2 = new Integrante();
            integrante2.setFranquia("Apex Legends");
            integrante2.setNome("Lifeline");
            integrante2.setFuncao("Support");
            integranteService.salvar(integrante2);
            
            Integrante integrante3 = new Integrante();
            integrante3.setFranquia("Counter Strike");
            integrante3.setNome("S1mple");
            integrante3.setFuncao("AWP");
            integranteService.salvar(integrante3);
            
            Integrante integrante4 = new Integrante();
            integrante4.setFranquia("Counter Strike");
            integrante4.setNome("ZywOo");
            integrante4.setFuncao("Rifler");
            integranteService.salvar(integrante4);

            Time time1 = new Time();
            time1.setData(LocalDate.of(2024, 1, 1));
            timeService.salvar(time1, Arrays.asList(integrante1.getId(), integrante2.getId()));
            
            Time time2 = new Time();
            time2.setData(LocalDate.of(2024, 1, 2));
            timeService.salvar(time2, Arrays.asList(integrante3.getId(), integrante4.getId()));
            
            return "Dados populados com sucesso!";
        } catch (Exception e) {
            return "Erro ao popular dados: " + e.getMessage();
        }
    }
}
