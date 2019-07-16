package com.ratacheski.gerenciadortarefas.controllers;

import com.ratacheski.gerenciadortarefas.models.Tarefa;
import com.ratacheski.gerenciadortarefas.models.Usuario;
import com.ratacheski.gerenciadortarefas.repositories.TarefaRepository;
import com.ratacheski.gerenciadortarefas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ModelAndView listarTarefas(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tarefas/listar");
        String emailUsuario = request.getUserPrincipal().getName();
        modelAndView.addObject("tarefas", tarefaRepository.carregarTarefasPorUsuario(emailUsuario));
        return modelAndView;
    }

    @GetMapping("/inserir")
    public ModelAndView inserir() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tarefas/inserir");
        modelAndView.addObject("tarefa", new Tarefa());
        return modelAndView;
    }

    @PostMapping("/inserir")
    public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (tarefa.getDataExpiracao() == null) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoNula", "A data de Expiração não pode ser nula");
        } else if (tarefa.getDataExpiracao().before(new Date())) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de Expiração não pode ser anterior à data Atual");
        }
        if (result.hasErrors()) {
            modelAndView.setViewName("redirect:/tarefas/inserir");
            modelAndView.addObject(tarefa);
        } else {
            String emailUsuario = request.getUserPrincipal().getName();
            Usuario usuario = usuarioService.findByEmail(emailUsuario);
            tarefa.setUsuario(usuario);
            tarefaRepository.save(tarefa);
            modelAndView.setViewName("redirect:/tarefas/listar");
        }
        return modelAndView;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Tarefa tarefa = tarefaRepository.getOne(id);
        modelAndView.setViewName("tarefas/alterar");
        modelAndView.addObject("tarefa", tarefa);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (tarefa.getDataExpiracao() == null) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoNula", "A data de Expiração não pode ser nula");
        } else if (tarefa.getDataExpiracao().before(new Date())) {
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de Expiração não pode ser anterior à data Atual");
        }
        if (result.hasErrors()) {
            modelAndView.setViewName("redirect:/tarefas/alterar");
            modelAndView.addObject(tarefa);
        } else {
            modelAndView.setViewName("redirect:/tarefas/listar");
            tarefaRepository.save(tarefa);
        }
        return modelAndView;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id){
        tarefaRepository.deleteById(id);
        return "redirect:/tarefas/listar";
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable("id") Long id){
        Tarefa tarefa = tarefaRepository.getOne(id);
        tarefa.setConcluida(true);
        tarefaRepository.save(tarefa);
        return "redirect:/tarefas/listar";
    }
}
