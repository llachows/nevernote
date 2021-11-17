package com.example.nevernote;

import com.example.nevernote.model.dto.NoteDto;
import com.example.nevernote.model.dto.NotebookDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetrieveFilteredNotebookNOtesApiIntegrationTest extends BaseIntegrationTest {

  @Sql(
      statements = {
        "INSERT INTO notebook VALUES ('674da7e7-77c5-4b97-b692-eb215b298165');",
        "INSERT INTO note "
            + "VALUES ('30ada802-1a63-47f3-b3a4-c5058b1f5970', "
            + "        'test note 1', "
            + "        'example body 1', "
            + "        'TAG_1;TAG_2', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '674da7e7-77c5-4b97-b692-eb215b298165'); ",
        "INSERT INTO note "
            + "VALUES ('5fdb8803-c84a-4ead-b681-254b80616fc0', "
            + "        'test note 2', "
            + "        'example body 2', "
            + "        'TAG_1;TAG_3', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '674da7e7-77c5-4b97-b692-eb215b298165'); ",
        "INSERT INTO note "
            + "VALUES ('12ae9f76-b789-4abc-b44b-5cc0fd9c737c', "
            + "        'test note 3', "
            + "        'example body 3', "
            + "        'TAG_4', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '2021-11-16 18:00:00.000Z', "
            + "        '674da7e7-77c5-4b97-b692-eb215b298165');"
      })
  @Test
  public void test_retrieve_filtered_note() {
    // given
    var notebookId = "674da7e7-77c5-4b97-b692-eb215b298165";
    String tag = "TAG_1";

    // when
    var response =
        restTemplate.getForEntity(
            serverBaseUrl() + notebookUrlWithId(notebookId) + "?tag=" + tag, NotebookDto.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var dto = response.getBody();
    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.getNumberOfNotes()).isEqualTo(2);
    assertThat(dto.getNotes())
        .hasSize(2)
        .contains(
            NoteDto.builder()
                .id("30ada802-1a63-47f3-b3a4-c5058b1f5970")
                .title("test note 1")
                .body("example body 1")
                .tags(List.of("TAG_1", "TAG_2"))
                .created(Instant.parse("2021-11-16T18:00:00.000Z"))
                .lastModified(Instant.parse("2021-11-16T18:00:00.000Z"))
                .build(),
            NoteDto.builder()
                .id("5fdb8803-c84a-4ead-b681-254b80616fc0")
                .title("test note 2")
                .body("example body 2")
                .tags(List.of("TAG_1", "TAG_3"))
                .created(Instant.parse("2021-11-16T18:00:00.000Z"))
                .lastModified(Instant.parse("2021-11-16T18:00:00.000Z"))
                .build());
  }
}
