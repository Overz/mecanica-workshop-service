package com.fiap.mecanica.workshop.infra.persistence.repository;

import com.fiap.mecanica.workshop.infra.persistence.document.MecanicoDocument;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MecanicoMongoRepository extends MongoRepository<MecanicoDocument, UUID> {

  Optional<MecanicoDocument> findFirstBy();
}
