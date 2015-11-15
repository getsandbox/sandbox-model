package com.sandbox.common.models.jms;

import com.sandbox.common.models.RuntimeRequest;
import com.sandbox.common.enums.RuntimeTransportType;

/**
 * Created by nickhoughton on 1/08/2014.
 */
public class JMSRuntimeRequest extends RuntimeRequest {

    String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getTransport() {
        return RuntimeTransportType.JMS.toString();
    }
}
