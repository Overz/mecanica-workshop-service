package com.fiap.mecanica.workshop.infra.persistence.document;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mecanicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MecanicoDocument {

  @Id
  private UUID id;
  private String nome;
  private String especialidade;
}
