package com.ratacheski.gerenciadortarefas.repositories;

import com.ratacheski.gerenciadortarefas.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

}
