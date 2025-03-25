package com.javanauta.agendadortarefas.business.mapper;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

            @Mapping(source = "id", target = "id")
            @Mapping(source = "dataEvento", target = "dataEvento")
            @Mapping(source = "dataCriacao", target = "dataCriacao")

//    Implementa tudo que a gente faz para realizar a conversão de Entity para DTO e vice versa
    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);

    List<TarefasEntity> paraTarefaEntityList(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> entities);
}
