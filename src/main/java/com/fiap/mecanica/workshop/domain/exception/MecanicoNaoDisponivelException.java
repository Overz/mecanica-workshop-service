package com.fiap.mecanica.workshop.domain.exception;

public class MecanicoNaoDisponivelException extends RuntimeException {
  public MecanicoNaoDisponivelException() {
    super("Nenhum mecânico disponível para atribuição");
  }
}
