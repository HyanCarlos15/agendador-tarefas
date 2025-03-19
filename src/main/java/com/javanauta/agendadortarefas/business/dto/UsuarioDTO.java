package com.javanauta.agendadortarefas.business.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private String email;
    private String senha;
}
