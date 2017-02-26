package com.sandbox.runtime.models;

public interface RepositoryService {

    String getRepositoryFile(String fullSandboxId, String filename);

    boolean hasRepositoryFile(String fullSandboxId, String filename);
}
