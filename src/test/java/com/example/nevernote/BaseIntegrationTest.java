package com.example.nevernote;

import com.example.nevernote.model.entity.NoteEntity;
import com.example.nevernote.model.entity.NotebookEntity;
import com.example.nevernote.repository.NoteRepository;
import com.example.nevernote.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.Optional;
import java.util.UUID;

@Sql(
    statements = {
      "SET REFERENTIAL_INTEGRITY FALSE",
      "TRUNCATE TABLE note;",
      "TRUNCATE TABLE notebook;",
      "SET REFERENTIAL_INTEGRITY TRUE",
    },
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIntegrationTest {
  public static final String UUID_REGEXP =
      "[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?";
  public static final String NOTES_URL = "v1/api/notes";
  public static final String NOTE_URL_REGEXP = String.format("^/%s/%s$", NOTES_URL, UUID_REGEXP);
  public static final String NOTEBOOKS_URL = "v1/api/notebooks";
  public static final String NOTEBOOK_URL_REGEXP =
      String.format("^/%s/%s$", NOTEBOOKS_URL, UUID_REGEXP);

  @LocalServerPort protected int port;

  @Autowired protected TestRestTemplate restTemplate;

  @Autowired protected NotebookRepository notebookRepository;
  @Autowired protected NoteRepository noteRepository;

  protected String serverBaseUrl() {
    return String.format("http://localhost:%d/", port);
  }

  protected String noteUrlWithId(String noteId) {
    return String.format("%s/%s", NOTES_URL, noteId);
  }

  protected String notebookUrlWithId(String notebookId) {
    return String.format("%s/%s", NOTEBOOKS_URL, notebookId);
  }

  protected Optional<NoteEntity> findNote(String uuid) {
    return noteRepository.findById(UUID.fromString(uuid));
  }

  protected Optional<NotebookEntity> findNotebook(String uuid) {
    return notebookRepository.findById(UUID.fromString(uuid));
  }
}
