package com.fiap.mecanica.workshop.domain.service;

import com.fiap.mecanica.workshop.application.messaging.ExecucaoFinalizadaEvent;
import com.fiap.mecanica.workshop.application.messaging.FalhaNaExecucaoEvent;
import com.fiap.mecanica.workshop.application.messaging.IniciarExecucaoCommand;
import com.fiap.mecanica.workshop.domain.enums.StatusExecucao;
import com.fiap.mecanica.workshop.domain.exception.MecanicoNaoDisponivelException;
import com.fiap.mecanica.workshop.infra.messaging.publisher.WorkshopEventPublisher;
import com.fiap.mecanica.workshop.infra.persistence.document.ExecucaoDocument;
import com.fiap.mecanica.workshop.infra.persistence.document.MecanicoDocument;
import com.fiap.mecanica.workshop.infra.persistence.repository.ExecucaoMongoRepository;
import com.fiap.mecanica.workshop.infra.persistence.repository.MecanicoMongoRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkshopService {

  private final ExecucaoMongoRepository execucaoRepository;
  private final MecanicoMongoRepository mecanicoRepository;
  private final WorkshopEventPublisher publisher;

  public void executarReparo(IniciarExecucaoCommand command) {
    Optional<MecanicoDocument> mecanico = mecanicoRepository.findFirstBy();

    if (mecanico.isEmpty()) {
      log.warn("[WORKSHOP] Nenhum mecânico disponível sagaId={}", command.sagaId());
      publisher.publicarFalha(
          new FalhaNaExecucaoEvent(command.sagaId(), command.osId(), "Nenhum mecânico disponível"));
      return;
    }

    ExecucaoDocument execucao = ExecucaoDocument.builder()
        .id(UUID.randomUUID())
        .osId(command.osId())
        .sagaId(command.sagaId())
        .mecanicoId(mecanico.get().getId())
        .status(StatusExecucao.FINALIZADA)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    execucaoRepository.save(execucao);
    log.info("[WORKSHOP] Execução finalizada osId={} mecanicoId={} execucaoId={}",
        command.osId(), mecanico.get().getId(), execucao.getId());

    publisher.publicarSucesso(
        new ExecucaoFinalizadaEvent(command.sagaId(), command.osId(), execucao.getId()));
  }
}
