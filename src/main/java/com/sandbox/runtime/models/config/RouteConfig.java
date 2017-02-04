package com.sandbox.runtime.models.config;

public class RouteConfig {

    //details of route being targetted
    private String method;
    private String path;

    //force delay/latency config
    private LatencyStrategyEnum latencyType = LatencyStrategyEnum.NONE;
    private int latencyMs = 0;
    private int latencyMultiplier = 1;

    //forced error behaviour config
    private ErrorStrategyEnum errorStrategy;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LatencyStrategyEnum getLatencyType() {
        return latencyType;
    }

    public void setLatencyType(LatencyStrategyEnum latencyType) {
        this.latencyType = latencyType;
    }

    public int getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(int latencyMs) {
        this.latencyMs = latencyMs;
    }

    public int getLatencyMultiplier() {
        return latencyMultiplier;
    }

    public void setLatencyMultiplier(int latencyMultiplier) {
        this.latencyMultiplier = latencyMultiplier;
    }

    public ErrorStrategyEnum getErrorStrategy() {
        return errorStrategy;
    }

    public void setErrorStrategy(ErrorStrategyEnum errorStrategy) {
        this.errorStrategy = errorStrategy;
    }

    public void validate(){
        if(method == null){
            throw new IllegalArgumentException("Missing route parameter: method");
        };
        if(path == null){
            throw new IllegalArgumentException("Missing route parameter: path");
        };
    }
}
