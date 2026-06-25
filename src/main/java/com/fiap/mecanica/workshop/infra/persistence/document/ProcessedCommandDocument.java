package com.fiap.mecanica.workshop.infra.persistence.document;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "processed_commands")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedCommandDocument {

  @Id
  private UUID id;

  @Indexed(unique = true)
  private UUID sagaId;

  private LocalDateTime processedAt;
}
