package com.fiap.mecanica.workshop.application.messaging;

import java.util.UUID;

public record FalhaNaExecucaoEvent(UUID sagaId, UUID osId, String motivo) {}
