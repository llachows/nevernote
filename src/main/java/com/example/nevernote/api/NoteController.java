package com.example.nevernote.api;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.service.DeleteNoteService;
import com.example.nevernote.service.RetrieveNoteService;
import com.example.nevernote.service.UpdateNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/notes")
@RequiredArgsConstructor
public class NoteController {

  private final UpdateNoteService updateNoteService;
  private final RetrieveNoteService retrieveNoteService;
  private final DeleteNoteService deleteNoteService;

  @PutMapping("/{noteId}")
  public ResponseEntity<NoteDto> updateNote(@PathVariable UUID noteId, @RequestBody NoteBody note) {
    var noteDto = updateNoteService.execute(noteId, note);
    return ResponseEntity.ok(noteDto);
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<NoteDto> retrieveNote(@PathVariable UUID noteId) {
    var noteDto = retrieveNoteService.execute(noteId);
    return ResponseEntity.ok(noteDto);
  }

  @DeleteMapping("/{noteId}")
  public ResponseEntity<NoteDto> deleteNote(@PathVariable UUID noteId) {
    deleteNoteService.execute(noteId);
    return ResponseEntity.noContent().build();
  }
}
