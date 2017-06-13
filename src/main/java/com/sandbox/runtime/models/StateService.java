package com.sandbox.runtime.models;

public interface StateService {

    String getSandboxState(String sandboxId);

    void setSandboxState(String sandboxId, String state);

}
