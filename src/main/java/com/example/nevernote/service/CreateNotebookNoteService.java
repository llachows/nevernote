package com.example.nevernote.service;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.mapper.NoteEntityToNoteDtoMapper;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.entity.NoteEntity;
import com.example.nevernote.repository.NoteRepository;
import com.example.nevernote.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateNotebookNoteService {

  private final NoteRepository noteRepository;
  private final NotebookRepository notebookRepository;
  private final NoteEntityToNoteDtoMapper noteEntityToNoteDtoMapper;

  public NoteDto execute(UUID notebookId, NoteBody noteBody) {
    var notebookEntity =
        notebookRepository
            .findById(notebookId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Notebook not found: %s", notebookId)));

    var noteEntity =
        noteRepository.save(
            NoteEntity.builder()
                .title(noteBody.getTitle())
                .body(noteBody.getBody())
                .tags(noteBody.getTags())
                .notebook(notebookEntity)
                .build());

    return noteEntityToNoteDtoMapper.map(noteEntity);
  }
}
