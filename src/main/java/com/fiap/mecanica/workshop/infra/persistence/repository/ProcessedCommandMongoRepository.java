package com.fiap.mecanica.workshop.infra.persistence.repository;

import com.fiap.mecanica.workshop.infra.persistence.document.ProcessedCommandDocument;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedCommandMongoRepository extends MongoRepository<ProcessedCommandDocument, UUID> {

  boolean existsBySagaId(UUID sagaId);
}
