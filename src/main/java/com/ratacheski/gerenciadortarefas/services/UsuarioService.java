package com.ratacheski.gerenciadortarefas.services;

import com.ratacheski.gerenciadortarefas.models.Usuario;
import com.ratacheski.gerenciadortarefas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public void salvar(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }
}
