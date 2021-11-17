package com.example.nevernote.repository;

import com.example.nevernote.model.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

  @Modifying
  @Query("delete from NoteEntity n where n.notebook.id = :notebookId")
  void deleteAllByNotebookId(@Param("notebookId") UUID notebookId);
}
