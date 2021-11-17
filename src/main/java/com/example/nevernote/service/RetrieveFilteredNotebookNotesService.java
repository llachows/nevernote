package com.example.nevernote.service;

import com.example.nevernote.mapper.NotebookEntityToNotebookDtoMapper;
import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.dto.NotebookDto;
import com.example.nevernote.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveFilteredNotebookNotesService {

  private final NotebookRepository notebookRepository;
  private final NotebookEntityToNotebookDtoMapper notebookEntityToNotebookDtoMapper;

  public NotebookDto execute(UUID notebookId, String tag) {
    var dto =
        notebookRepository
            .findById(notebookId)
            .map(notebookEntityToNotebookDtoMapper::map)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Notebook not found: %s", notebookId)));

    if (tag == null || tag.isBlank()) {
      return dto;
    }
    return filter(dto, tag);
  }

  private NotebookDto filter(NotebookDto dto, String tag) {
    List<NoteDto> filteredNotes =
        dto.getNotes().stream()
            .filter(note -> note.getTags().contains(tag))
            .collect(Collectors.toList());
    dto.setNotes(filteredNotes);
    dto.setNumberOfNotes(filteredNotes.size());
    return dto;
  }
}
