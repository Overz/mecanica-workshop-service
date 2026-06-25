package com.fiap.mecanica.workshop.infra.messaging.listener;

import com.fiap.mecanica.workshop.application.messaging.FalhaNaExecucaoEvent;
import com.fiap.mecanica.workshop.application.messaging.IniciarExecucaoCommand;
import com.fiap.mecanica.workshop.domain.service.WorkshopService;
import com.fiap.mecanica.workshop.infra.messaging.config.RabbitMqConfig;
import com.fiap.mecanica.workshop.infra.messaging.publisher.WorkshopEventPublisher;
import com.fiap.mecanica.workshop.infra.persistence.document.ProcessedCommandDocument;
import com.fiap.mecanica.workshop.infra.persistence.repository.ProcessedCommandMongoRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IniciarExecucaoListener {

  private final WorkshopService workshopService;
  private final WorkshopEventPublisher publisher;
  private final ProcessedCommandMongoRepository processedCommandRepo;

  @RabbitListener(queues = RabbitMqConfig.QUEUE_INICIAR_EXECUCAO)
  public void onIniciarExecucao(IniciarExecucaoCommand command) {
    log.info("[MQ] Recebido IniciarExecucaoCommand sagaId={} osId={}", command.sagaId(), command.osId());

    if (processedCommandRepo.existsBySagaId(command.sagaId())) {
      log.warn("[MQ] Comando duplicado ignorado sagaId={}", command.sagaId());
      return;
    }

    processedCommandRepo.save(ProcessedCommandDocument.builder()
        .id(UUID.randomUUID())
        .sagaId(command.sagaId())
        .processedAt(LocalDateTime.now())
        .build());

    try {
      workshopService.executarReparo(command);
    } catch (Exception e) {
      log.error("[WORKSHOP] Erro inesperado sagaId={}: {}", command.sagaId(), e.getMessage(), e);
      publisher.publicarFalha(new FalhaNaExecucaoEvent(command.sagaId(), command.osId(),
          "Erro interno no workshop-service: " + e.getMessage()));
    }
  }
}
