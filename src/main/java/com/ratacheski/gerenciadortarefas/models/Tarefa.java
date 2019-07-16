package com.ratacheski.gerenciadortarefas.models;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tar_tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tar_id")
    private Long id;

    @Column(name = "tar_titulo", length = 50, nullable = false)
    @NotNull(message = "Título é Obrigatório")
    @Length(max = 50, min = 3, message = "O Título deve conter entre 3 e 50 caracteres")
    private String titulo;

    @Column(name = "tar_descricao", length = 100)
    @Length(max = 100, message = "A Descrição deve conter até 100 caracteres")
    private String descricao;

    @Column(name = "tar_data_expiracao", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataExpiracao;

    @Column(name = "tar_concluida", nullable = false)
    private Boolean concluida = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
