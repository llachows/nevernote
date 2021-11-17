package com.example.nevernote.api.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Model representing note")
public class NoteBody {

  @Schema(required = true, description = "Title")
  @NotBlank(message = "Title is mandatory")
  private String title;

  @Schema(required = true, description = "Content")
  @NotBlank(message = "Body is mandatory")
  private String body;

  @Schema(required = true, description = "Tags")
  @NotEmpty(message = "Tags are mandatory")
  private List<String> tags;
}
