package com.fiap.mecanica.workshop.infra.messaging.publisher;

import com.fiap.mecanica.workshop.application.messaging.ExecucaoFinalizadaEvent;
import com.fiap.mecanica.workshop.application.messaging.FalhaNaExecucaoEvent;
import com.fiap.mecanica.workshop.infra.messaging.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkshopEventPublisher {

  private final RabbitTemplate rabbitTemplate;

  public void publicarSucesso(ExecucaoFinalizadaEvent event) {
    rabbitTemplate.convertAndSend(
        RabbitMqConfig.EXCHANGE, RabbitMqConfig.RK_EXECUCAO_FINALIZADA, event);
    log.info("[MQ] ExecucaoFinalizadaEvent publicado sagaId={} execucaoId={}",
        event.sagaId(), event.execucaoId());
  }

  public void publicarFalha(FalhaNaExecucaoEvent event) {
    rabbitTemplate.convertAndSend(
        RabbitMqConfig.EXCHANGE, RabbitMqConfig.RK_FALHA_EXECUCAO, event);
    log.warn("[MQ] FalhaNaExecucaoEvent publicado sagaId={} motivo={}",
        event.sagaId(), event.motivo());
  }
}
