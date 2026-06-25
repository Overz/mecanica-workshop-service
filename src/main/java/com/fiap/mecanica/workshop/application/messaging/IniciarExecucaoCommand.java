package com.fiap.mecanica.workshop.application.messaging;

import java.util.UUID;

public record IniciarExecucaoCommand(UUID sagaId, UUID osId) {}
