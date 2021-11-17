package com.example.nevernote.mapper;

import com.example.nevernote.model.dto.NotebookDto;
import com.example.nevernote.model.entity.NotebookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotebookEntityToNotebookDtoMapper {

  private final NoteEntityToNoteDtoMapper noteEntityToNoteDtoMapper;

  public NotebookDto map(NotebookEntity entity) {
    return NotebookDto.builder()
        .id(entity.getId().toString())
        .notes(
            entity.getNotes().stream()
                .map(noteEntityToNoteDtoMapper::map)
                .collect(Collectors.toList()))
        .numberOfNotes(entity.getNotes().size())
        .build();
  }
}
