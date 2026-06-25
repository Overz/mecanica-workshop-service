package com.fiap.mecanica.workshop.infra.seeding;

import com.fiap.mecanica.workshop.infra.persistence.document.MecanicoDocument;
import com.fiap.mecanica.workshop.infra.persistence.repository.MecanicoMongoRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkshopSeedingRunner implements CommandLineRunner {

  private final MecanicoMongoRepository mecanicoRepository;

  @Value("${seeding.enabled:true}")
  private boolean seedingEnabled;

  @Override
  public void run(String... args) {
    if (!seedingEnabled || mecanicoRepository.count() > 0) {
      log.info("[SEEDING] Workshop seeding ignorado (desabilitado ou já populado)");
      return;
    }

    List<MecanicoDocument> mecanicos = List.of(
        MecanicoDocument.builder()
            .id(UUID.fromString("a1b2c3d4-0001-0001-0001-000000000001"))
            .nome("Carlos Silva")
            .especialidade("Motor")
            .build(),
        MecanicoDocument.builder()
            .id(UUID.fromString("a1b2c3d4-0002-0002-0002-000000000002"))
            .nome("Ana Souza")
            .especialidade("Suspensão")
            .build()
    );

    mecanicoRepository.saveAll(mecanicos);
    log.info("[SEEDING] {} mecânicos inseridos no workshop-service", mecanicos.size());
  }
}
