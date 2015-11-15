package com.sandbox.common.models;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nickhoughton on 9/08/2014.
 */
public class InstanceTransaction {

    String sandboxName;

    RuntimeRequest request;
    List<RuntimeResponse> responses;

    public InstanceTransaction(RuntimeRequest request, RuntimeResponse... responses) {
        this(request, Arrays.asList(responses));
    }

    public InstanceTransaction(RuntimeRequest request, List<RuntimeResponse> responses) {
        sandboxName = request.getSandboxName();
        this.request = request;
        this.responses = responses;

        //calculate duration
        for (RuntimeResponse response : this.responses){
            if(response.getRespondedTimestamp() != null){
                response.setDurationMillis(response.getRespondedTimestamp() - request.getReceivedTimestamp());
            }
        }
    }

    public RuntimeRequest getRequest() {
        return request;
    }

    public List<RuntimeResponse> getResponses() {
        return responses;
    }

}
