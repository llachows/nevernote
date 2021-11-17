package com.example.nevernote;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteNotebookNotesApiIntegrationTest extends BaseIntegrationTest {

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
  public void test_delete_notes_from_notebook() {
    // given
    var request = new HttpEntity<>(new HttpHeaders());
    var notebookId = "674da7e7-77c5-4b97-b692-eb215b298165";

    // when
    var response =
        restTemplate.exchange(
            serverBaseUrl() + notebookUrlWithId(notebookId) + "/notes",
            HttpMethod.DELETE,
            request,
            Void.class);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(findNotebook(notebookId)).isNotEmpty();
    assertThat(findNote("30ada802-1a63-47f3-b3a4-c5058b1f5970")).isEmpty();
    assertThat(findNote("5fdb8803-c84a-4ead-b681-254b80616fc0")).isEmpty();
    assertThat(findNote("12ae9f76-b789-4abc-b44b-5cc0fd9c737c")).isEmpty();
  }
}
