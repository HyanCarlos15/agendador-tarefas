package com.javanauta.agendadortarefas.business;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.javanauta.agendadortarefas.business.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.exceptions.ResouceNotFoundException;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefasService {

//    Injeção de Dependencias
    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    //    metodo para gravar a tarefa com o status da notificação e chamar tarefa Entity oara Dto
    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dto);
        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadorPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTO
                (tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletaTarefasPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResouceNotFoundException e) {
            throw new ResouceNotFoundException("Erro ao deletar tarefas por id, id ienxistente " + id,
                    e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity tarefas = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResouceNotFoundException("Tarefa não encontrada " + id));
            tarefas.setStatusNotificacaoEnum(status);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(tarefas));
        } catch (ResouceNotFoundException e) {
            throw new ResouceNotFoundException("Erro ao alterar status da tarefa " + id, e.getCause());
        }

    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
           TarefasEntity entity = tarefasRepository.findById(id).
                   orElseThrow(() -> new ResouceNotFoundException("Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));

        } catch (ResouceNotFoundException e) {
            throw new ResouceNotFoundException("Erro ao deletar tarefas por id, id ienxistente " + id,
                    e.getCause());
        }

    }
}
