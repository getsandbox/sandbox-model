package com.sandbox.runtime.models;

public interface RoutingTableCache {

    void setRoutingTableForSandboxId(String sandboxId, String fullSandboxId, RoutingTable routingTable);

    RoutingTable getRoutingTableForSandboxId(String sandboxId, String fullSandboxId);

}
