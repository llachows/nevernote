package com.example.nevernote.api.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Model representing notebook")
public class NotebookBody {

  @Singular
  @Schema(description = "Collection of notes")
  private List<NoteBody> notes;
}
