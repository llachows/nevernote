package com.example.nevernote.service;

import com.example.nevernote.mapper.NoteEntityToNoteDtoMapper;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveNoteService {

  private final NoteRepository noteRepository;
  private final NoteEntityToNoteDtoMapper noteEntityToNoteDtoMapper;

  public NoteDto execute(UUID noteId) {
    var entity =
        noteRepository
            .findById(noteId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Note not found: %s", noteId)));

    return noteEntityToNoteDtoMapper.map(entity);
  }
}
