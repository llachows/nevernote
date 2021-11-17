package com.example.nevernote.service;

import com.example.nevernote.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteNotebookService {

  private final NotebookRepository notebookRepository;

  public void execute(UUID notebookId) {
    notebookRepository.deleteById(notebookId);
  }
}
