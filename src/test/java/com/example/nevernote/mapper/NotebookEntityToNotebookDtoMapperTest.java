package com.example.nevernote.mapper;

import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.entity.NoteEntity;
import com.example.nevernote.model.entity.NotebookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class NotebookEntityToNotebookDtoMapperTest {

  @Mock private NoteEntityToNoteDtoMapper noteEntityToNoteDtoMapper;
  @InjectMocks private NotebookEntityToNotebookDtoMapper notebookEntityToNotebookDtoMapper;

  @Test
  public void test_map() {
    // given
    var entity = new NotebookEntity();
    entity.setId(UUID.fromString("f3b18f8c-6e5a-4f87-bdf6-86bd1f3e08d6"));
    var note = mock(NoteEntity.class);
    entity.setNotes(List.of(note));
    NoteDto noteDto = new NoteDto();
    given(noteEntityToNoteDtoMapper.map(note)).willReturn(noteDto);

    // when
    var dto = notebookEntityToNotebookDtoMapper.map(entity);

    // then
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo("f3b18f8c-6e5a-4f87-bdf6-86bd1f3e08d6");
    assertThat(dto.getNotes()).hasSize(1);
    assertThat(dto.getNotes()).contains(noteDto);
    assertThat(dto.getNumberOfNotes()).isEqualTo(1);
  }
}
