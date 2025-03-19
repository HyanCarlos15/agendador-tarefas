package com.javanauta.agendadortarefas.infrastructure.client;


import com.javanauta.agendadortarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "usuario", url = "${usuario.url}")

 public interface UsuarioClient {

        @GetMapping // Não precisa de um RI, pois usa por padrão que seria o Usuario
        UsuarioDTO buscaUsuarioPorEmail (@RequestParam("email") String email,
                @RequestHeader("Authorization") String token);


}
