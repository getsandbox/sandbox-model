package com.sandbox.runtime.models;

import java.util.Map;

public interface MetadataService {

    Map<String, String> getConfigForSandboxId(String sandboxId);

}
