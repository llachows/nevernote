package com.example.nevernote.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

  @Schema(description = "Note identifier")
  private String id;

  @Schema(required = true, description = "Title")
  private String title;

  @Schema(required = true, description = "Content")
  private String body;

  @Schema(required = true, description = "Tags")
  private List<String> tags;

  @Schema(description = "Creation timestamp")
  private Instant created;

  @Schema(description = "Last modification timestamp")
  private Instant lastModified;
}
