package com.fiap.mecanica.workshop.infra.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  public static final String EXCHANGE = "mecanica.direct";

  // M3 — workshop
  public static final String QUEUE_INICIAR_EXECUCAO    = "mecanica.workshop.iniciar-execucao";
  public static final String QUEUE_EXECUCAO_FINALIZADA = "mecanica.os.execucao-finalizada";
  public static final String QUEUE_FALHA_EXECUCAO      = "mecanica.os.falha-execucao";
  public static final String RK_INICIAR_EXECUCAO       = "workshop.iniciar-execucao";
  public static final String RK_EXECUCAO_FINALIZADA    = "os.execucao-finalizada";
  public static final String RK_FALHA_EXECUCAO         = "os.falha-execucao";

  @Bean
  DirectExchange mecanicaExchange() {
    return new DirectExchange(EXCHANGE, true, false);
  }

  @Bean
  Queue filaIniciarExecucao() {
    return QueueBuilder.durable(QUEUE_INICIAR_EXECUCAO).build();
  }

  @Bean
  Queue filaExecucaoFinalizada() {
    return QueueBuilder.durable(QUEUE_EXECUCAO_FINALIZADA).build();
  }

  @Bean
  Queue filaFalhaExecucao() {
    return QueueBuilder.durable(QUEUE_FALHA_EXECUCAO).build();
  }

  @Bean
  Binding bindingIniciarExecucao(Queue filaIniciarExecucao, DirectExchange mecanicaExchange) {
    return BindingBuilder.bind(filaIniciarExecucao).to(mecanicaExchange).with(RK_INICIAR_EXECUCAO);
  }

  @Bean
  Binding bindingExecucaoFinalizada(Queue filaExecucaoFinalizada, DirectExchange mecanicaExchange) {
    return BindingBuilder.bind(filaExecucaoFinalizada).to(mecanicaExchange).with(RK_EXECUCAO_FINALIZADA);
  }

  @Bean
  Binding bindingFalhaExecucao(Queue filaFalhaExecucao, DirectExchange mecanicaExchange) {
    return BindingBuilder.bind(filaFalhaExecucao).to(mecanicaExchange).with(RK_FALHA_EXECUCAO);
  }

  @Bean
  Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
      Jackson2JsonMessageConverter converter) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(converter);
    return template;
  }
}
