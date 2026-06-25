package com.fiap.mecanica.workshop.presentation.controller;

import com.fiap.mecanica.workshop.infra.persistence.document.ExecucaoDocument;
import com.fiap.mecanica.workshop.infra.persistence.repository.ExecucaoMongoRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/execucoes")
@RequiredArgsConstructor
public class ExecucaoController {

  private final ExecucaoMongoRepository execucaoRepository;

  @GetMapping("/{id}")
  public ResponseEntity<ExecucaoDocument> buscarPorId(@PathVariable UUID id) {
    return execucaoRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/os/{osId}")
  public ResponseEntity<ExecucaoDocument> buscarPorOs(@PathVariable UUID osId) {
    return execucaoRepository.findByOsId(osId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
