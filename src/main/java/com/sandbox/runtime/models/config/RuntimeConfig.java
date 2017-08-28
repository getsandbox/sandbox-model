package com.sandbox.runtime.models.config;

import com.sandbox.runtime.models.RuntimeVersion;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RuntimeConfig {

    //base runtime configuration
    Path basePath = Paths.get("./");
    int httpPort = 8080;
    Integer debugPort = 5005;
    Integer metadataPort = 8081;
    int activityDepth = 50;
    Path statePath;
    RuntimeVersion runtimeVersion = RuntimeVersion.getLatest();
    boolean verboseLogging = false;
    boolean disableLogging = false;
    boolean disableIDs = false;
    boolean enableConcurrency = false;

    //route specific configuration
    List<RouteConfig> routes = new ArrayList<>();

    public void validate(){
        if(basePath == null){
            throw new IllegalArgumentException("Missing argument: basePath");
        };
        if(!basePath.toFile().exists()){
            throw new IllegalArgumentException("Base path specified doesn't exist");
        };
        if(httpPort < 0){
            throw new IllegalArgumentException("Missing argument: httpPort");
        };
        if(runtimeVersion == null){
            throw new IllegalArgumentException("Missing argument: runtimeVersion");
        };

        routes.forEach(route -> route.validate());
    }

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        try{
            String basePathStr = basePath.toString();
            if(basePathStr == null) basePathStr = System.getProperty("user.dir");
            if(basePathStr.startsWith("~")) basePathStr = System.getProperty("user.home") + basePathStr.substring(1);
            this.basePath = Paths.get(basePathStr);
            //ensure the path actually exists
            this.basePath.toRealPath();
        }catch(Exception e){
            throw new IllegalArgumentException("Invalid base path");
        }
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public Integer getDebugPort() {
        return debugPort;
    }

    public void setDebugPort(Integer debugPort) {
        this.debugPort = debugPort;
    }

    public Integer getMetadataPort() {
        return metadataPort;
    }

    public void setMetadataPort(Integer metadataPort) {
        this.metadataPort = metadataPort;
    }

    public int getActivityDepth() {
        return activityDepth;
    }

    public void setActivityDepth(int activityDepth) {
        this.activityDepth = activityDepth;
    }

    public Path getStatePath() {
        return statePath;
    }

    public void setStatePath(Path statePath) {
        String statePathStr = statePath.toString();
        if(statePathStr != null) {
            try {
                if (statePathStr.startsWith("~"))
                    statePathStr = System.getProperty("user.home") + statePathStr.substring(1);
                this.statePath = Paths.get(statePathStr);

            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid state path");
            }
        }
    }

    public RuntimeVersion getRuntimeVersion() {
        return runtimeVersion;
    }

    public void setRuntimeVersion(RuntimeVersion runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }

    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    public boolean isDisableLogging() {
        return disableLogging;
    }

    public void setDisableLogging(boolean disableLogging) {
        this.disableLogging = disableLogging;
    }

    public boolean isDisableIDs() {
        return disableIDs;
    }

    public void setDisableIDs(boolean disableIDs) {
        this.disableIDs = disableIDs;
    }

    public boolean isEnableConcurrency() {
        return enableConcurrency;
    }

    public void setEnableConcurrency(boolean enableConcurrency) {
        this.enableConcurrency = enableConcurrency;
    }

    public List<RouteConfig> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteConfig> routes) {
        this.routes = routes;
    }
}
