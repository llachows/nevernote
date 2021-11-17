package com.example.nevernote.repository;

import com.example.nevernote.model.entity.NotebookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotebookRepository extends JpaRepository<NotebookEntity, UUID> {}
