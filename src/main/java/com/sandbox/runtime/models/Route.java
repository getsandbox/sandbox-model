package com.sandbox.runtime.models;

import com.sandbox.runtime.models.config.RouteConfig;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickhoughton on 3/08/2014.
 */
public abstract class Route implements Serializable{

    private static final long serialVersionUID = 7262164955602223539L;

    String transport;
    Map<String, String> properties;
    @ApiModelProperty(hidden = true)
    ScriptSource defineSource;

    //the type of define function call, can be define() or soap()
    @ApiModelProperty(hidden = true)
    String defineType;
    @ApiModelProperty(hidden = true)
    ScriptSource functionSource;

    RouteConfig routeConfig;

    public Route() {

    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Map<String, String> getProperties() {
        if(properties == null) properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public ScriptSource getDefineSource() {
        return defineSource;
    }

    public void setDefineSource(ScriptSource defineSource) {
        this.defineSource = defineSource;
    }

    public String getDefineType() {
        return defineType;
    }

    public void setDefineType(String defineType) {
        this.defineType = defineType;
    }

    public ScriptSource getFunctionSource() {
        return functionSource;
    }

    public void setFunctionSource(ScriptSource functionSource) {
        this.functionSource = functionSource;
    }

    //match explicit properties
    public boolean matchesProperties(Map<String, String> properties){
        if(properties == null) properties = new HashMap<>();

        boolean match = true;
        for (Map.Entry<String, String> entry : getProperties().entrySet()){
            if(!properties.getOrDefault(entry.getKey(), "").equalsIgnoreCase(entry.getValue().trim())) {
                match = false;
                break;
            }
        }
        return match;
    }

    public RouteConfig getRouteConfig() {
        return routeConfig;
    }

    public void setRouteConfig(RouteConfig routeConfig) {
        this.routeConfig = routeConfig;
    }

    public abstract String getProcessingKey();

    public abstract String getDisplayKey();

    public abstract boolean isMatch(Route otherRoute);

    public abstract boolean isMatch(RuntimeRequest runtimeRequest);

    public abstract boolean isUncompiledMatch(EngineRequest req);

}
