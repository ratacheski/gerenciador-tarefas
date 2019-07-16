package com.ratacheski.gerenciadortarefas.controllers;

import com.ratacheski.gerenciadortarefas.models.Usuario;
import com.ratacheski.gerenciadortarefas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(){
        return "conta/login";
    }

    @GetMapping("/registration")
    public ModelAndView registrar() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("conta/registrar");
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registrar(@Valid Usuario usuario, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuario1 = usuarioService.findByEmail(usuario.getEmail());
        if (usuario1 != null) {
            result.rejectValue("email", "", "Usuário já Cadastrado!");
        } else if ( usuario.getSenha() == ""){
            result.rejectValue("senha", "", "Senha não pode ser Nula!");
        }
        if (result.hasErrors()){
            modelAndView.setViewName("conta/registrar");
            modelAndView.addObject("usuario", usuario);
        } else {
            usuarioService.salvar(usuario);
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}
