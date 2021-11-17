package com.example.nevernote.api;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.api.body.NotebookBody;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.dto.NotebookDto;
import com.example.nevernote.service.CreateNotebookNoteService;
import com.example.nevernote.service.CreateNotebookService;
import com.example.nevernote.service.DeleteNotebookNotesService;
import com.example.nevernote.service.DeleteNotebookService;
import com.example.nevernote.service.RetrieveFilteredNotebookNotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/notebooks")
@RequiredArgsConstructor
public class NotebookController {

  private final CreateNotebookNoteService createNotebookNoteService;
  private final CreateNotebookService createNotebookService;
  private final RetrieveFilteredNotebookNotesService retrieveFilteredNotebookNotesService;
  private final DeleteNotebookService deleteNotebookService;
  private final DeleteNotebookNotesService deleteNotebookNotesService;

  @PostMapping
  public ResponseEntity<NotebookDto> createNotebook(@Valid @RequestBody NotebookBody notebookBody) {
    var notebookDto = createNotebookService.execute(notebookBody);
    return ResponseEntity.created(URI.create("/v1/api/notebooks/" + notebookDto.getId()))
        .body(notebookDto);
  }

  @GetMapping("/{notebookId}")
  public ResponseEntity<NotebookDto> getNotebook(
      @PathVariable UUID notebookId, @RequestParam(required = false) String tag) {
    NotebookDto notebookDto = retrieveFilteredNotebookNotesService.execute(notebookId, tag);
    return ResponseEntity.ok(notebookDto);
  }

  @DeleteMapping("/{notebookId}")
  public ResponseEntity<Void> deleteNotebook(@PathVariable UUID notebookId) {
    deleteNotebookService.execute(notebookId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{notebookId}/notes")
  public ResponseEntity<NoteDto> createNote(
      @PathVariable UUID notebookId, @Valid @RequestBody NoteBody noteBody) {
    var noteDto = createNotebookNoteService.execute(notebookId, noteBody);
    return ResponseEntity.created(URI.create("/v1/api/notes/" + noteDto.getId())).body(noteDto);
  }

  @DeleteMapping("/{notebookId}/notes")
  public ResponseEntity<Void> deleteNotes(@PathVariable UUID notebookId) {
    deleteNotebookNotesService.execute(notebookId);
    return ResponseEntity.noContent().build();
  }
}
