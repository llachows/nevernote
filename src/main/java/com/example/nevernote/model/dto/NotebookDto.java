package com.example.nevernote.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Model representing notebook")
public class NotebookDto {

  @Schema(description = "Notebook identifier")
  private String id;

  @Schema(description = "Collection of notes")
  private List<NoteDto> notes;

  @Schema(description = "Number of notes")
  private Integer numberOfNotes;
}
