package com.fiap.mecanica.workshop.infra.persistence.repository;

import com.fiap.mecanica.workshop.infra.persistence.document.ExecucaoDocument;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExecucaoMongoRepository extends MongoRepository<ExecucaoDocument, UUID> {

  Optional<ExecucaoDocument> findByOsId(UUID osId);
}
