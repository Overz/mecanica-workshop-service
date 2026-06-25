package com.fiap.mecanica.workshop.application.messaging;

import java.util.UUID;

public record ExecucaoFinalizadaEvent(UUID sagaId, UUID osId, UUID execucaoId) {}
