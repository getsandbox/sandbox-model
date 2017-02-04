package com.sandbox.runtime.models.jms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandbox.runtime.models.RuntimeRequest;
import com.sandbox.runtime.models.EngineRequest;
import com.sandbox.runtime.models.Route;

import java.util.Map;

/**
 * Created by nickhoughton on 3/08/2014.
 */
public class JMSRoute extends Route {

    public JMSRoute() {
    }

    public JMSRoute(String destination, Map<String, String> properties) {
        this.destination = destination;
        setProperties(properties);
    }

    String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonIgnore
    @Override
    public String getProcessingKey() {
        return getDestination();
    }

    @JsonIgnore
    @Override
    public String getDisplayKey() {
        return getDestination();
    }

    @Override
    public boolean isMatch(Route otherRoute) {
        return otherRoute instanceof JMSRoute && getDestination().equals(((JMSRoute) otherRoute).getDestination());
    }

    @Override
    public boolean isMatch(RuntimeRequest runtimeRequest) {
        return runtimeRequest instanceof JMSRuntimeRequest && getDestination().equals(((JMSRuntimeRequest) runtimeRequest).getDestination());
    }

    //matches based on uncompiled path /blah/{smth}
    public boolean isUncompiledMatch(EngineRequest req){
        return req instanceof JMSRequest && getDestination().equals(((JMSRequest)req).destination());
    }
}
