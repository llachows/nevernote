package com.example.nevernote;

import com.example.nevernote.api.body.NoteBody;
import com.example.nevernote.model.dto.NoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateNotebookNoteApiIntegrationTest extends BaseIntegrationTest {

  @Sql(
      statements =
          "INSERT INTO notebook VALUES ('674da7e7-77c5-4b97-b692-eb215b298165');")
  @Test
  public void test_create_notebook_note() {
    // given
    var body =
        NoteBody.builder()
            .title("example title")
            .body("example body")
            .tags(List.of("TAG_NEW"))
            .build();
    var request = new HttpEntity<>(body, new HttpHeaders());
    String notebookId = "674da7e7-77c5-4b97-b692-eb215b298165";

    // when
    var response =
        restTemplate.postForEntity(
            serverBaseUrl() + notebookUrlWithId(notebookId) + "/notes", request, NoteDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getLocation())
        .matches(uri -> uri.toString().matches(NOTE_URL_REGEXP));
    var dto = response.getBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.getTitle()).isEqualTo("example title");
    assertThat(dto.getBody()).isEqualTo("example body");
    assertThat(dto.getTags()).contains("TAG_NEW");
    assertThat(dto.getCreated()).isNotNull();
    assertThat(dto.getLastModified()).isNotNull();
  }
}
