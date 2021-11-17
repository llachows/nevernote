package com.example.nevernote;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.api.body.NotebookBody;
import com.example.nevernote.model.dto.NotebookDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateNotebookApiIntegrationTest extends BaseIntegrationTest {

  @Test
  public void test_create_empty_notebook() {
    // given
    var body = NotebookBody.builder().build();
    var request = new HttpEntity<>(body, new HttpHeaders());

    // when
    var response =
        restTemplate.postForEntity(serverBaseUrl() + NOTEBOOKS_URL, request, NotebookDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getLocation())
        .matches(uri -> uri.toString().matches(NOTEBOOK_URL_REGEXP));
    var dto = response.getBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.getNotes()).isEmpty();
  }

  @Test
  public void test_create_notebook_with_notes() {
    // given
    var body =
        NotebookBody.builder()
            .note(
                NoteBody.builder()
                    .title("example title")
                    .body("example body")
                    .tags(List.of("TAG_1", "TAG_2"))
                    .build())
            .build();
    var request = new HttpEntity<>(body, new HttpHeaders());

    // when
    var response =
        restTemplate.postForEntity(serverBaseUrl() + NOTEBOOKS_URL, request, NotebookDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getLocation())
        .matches(uri -> uri.toString().matches(NOTEBOOK_URL_REGEXP));
    var notebookDto = response.getBody();
    assertThat(notebookDto).isNotNull();
    assertThat(notebookDto.getId()).isNotNull();
    assertThat(notebookDto.getNotes()).hasSize(1);
    var noteDto = notebookDto.getNotes().get(0);
    assertThat(noteDto.getId()).isNotNull();
    assertThat(noteDto.getTitle()).isEqualTo("example title");
    assertThat(noteDto.getBody()).isEqualTo("example body");
    assertThat(noteDto.getTags()).contains("TAG_1", "TAG_2");
    assertThat(noteDto.getCreated()).isNotNull();
    assertThat(noteDto.getLastModified()).isNotNull();
  }
}
