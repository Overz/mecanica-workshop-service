package com.fiap.mecanica.workshop.infra.persistence.document;

import com.fiap.mecanica.workshop.domain.enums.StatusExecucao;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "execucoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoDocument {

  @Id
  private UUID id;

  @Indexed
  private UUID osId;

  @Indexed
  private UUID sagaId;

  private UUID mecanicoId;
  private StatusExecucao status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
