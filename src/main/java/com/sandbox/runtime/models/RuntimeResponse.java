package com.sandbox.runtime.models;

import io.swagger.annotations.ApiModelProperty;

import java.lang.*;
import java.util.Map;

/**
 * Created by nickhoughton on 20/10/2014.
 */
public abstract class RuntimeResponse {
    @ApiModelProperty(value = "Which transport the request was for, 'HTTP' or 'JMS'.")
    protected String transport;
    @ApiModelProperty(value = "The body of the given response.")
    protected String body;
    @ApiModelProperty(value = "Transport headers for the given response.")
    protected Map<String, String> headers;
    @ApiModelProperty(value = "Error if there is a problem during Sandbox execution.")
    protected Error error;
    @ApiModelProperty(value = "The epoch time in milliseconds when the response was sent.")
    private long respondedTimestamp = System.currentTimeMillis();
    @ApiModelProperty(value = "Duration in milliseconds of the processing time in Sandbox.")
    private long durationMillis;
    @ApiModelProperty(value = "Duration in milliseconds of the response delay.")
    private int responseDelay = 0;

    public abstract String getTransport();

    public void setTransport(String transport) {
        //noop, only here to keep jackson happy.
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Error getError() {
        return error;
    }

    public boolean isError() {
        return (error != null);
    }

    public Long getRespondedTimestamp() {
        return respondedTimestamp;
    }

    public void setRespondedTimestamp(Long respondedTimestamp) {
        this.respondedTimestamp = respondedTimestamp;
    }

    public Long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(Long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public int getResponseDelay() {
        return responseDelay;
    }

    public void setResponseDelay(int responseDelay) {
        this.responseDelay = responseDelay;
    }
}
