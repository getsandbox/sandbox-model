package com.sandbox.runtime.models;

import com.sandbox.runtime.models.http.HttpRuntimeRequest;
import com.sandbox.runtime.models.jms.JMSRuntimeRequest;
import com.sandbox.runtime.models.http.HTTPRoute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nickhoughton on 3/08/2014.
 */
public class RoutingTable implements Serializable{

    private static final long serialVersionUID = 5416349625258357175L;
    String repositoryId;
    List<Route> routeDetails;
    private boolean routesSorted = false;

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<Route> getRouteDetails() {
        return routeDetails;
    }

    public void setRouteDetails(List<Route> routeDetails) {
        this.routeDetails = routeDetails;
    }

    public void addRouteDetails(Route routeDetails){
        if(getRouteDetails() == null){
            setRouteDetails(new ArrayList<Route>());
        }

        getRouteDetails().add(routeDetails);

    }

    public HTTPRoute findMatch(String requestMethod, String requestPath, Map<String, String> properties) {
        return (HTTPRoute) findMatch("HTTP", requestMethod, requestPath, properties);
    }

    public Route findMatch(String requestTransport, String requestMethod, String requestPath, Map<String, String> properties) {
        if("HTTP".equalsIgnoreCase(requestTransport)) {
            HttpRuntimeRequest request = new HttpRuntimeRequest();
            request.setMethod(requestMethod);
            request.setUrl(requestPath);
            request.setProperties(properties);
            return findMatch(request);
        }else if("JMS".equalsIgnoreCase(requestTransport)){
            JMSRuntimeRequest request = new JMSRuntimeRequest();
            request.setDestination(requestPath);
            request.setProperties(properties);
            return findMatch(request);
        }else{
            return null;
        }
    }

    public Route findMatch(RuntimeRequest runtimeRequest){
        List<Route> routes = getRouteDetails();

        //sort, put the longest route literals at the top, should theoretically be the best matches?!
        if(!routesSorted){
            routes.sort((r1, r2) -> {
                return r2.getProcessingKey().compareTo(r1.getProcessingKey());
            });
            routesSorted = true;
        }

        if(routes == null) return null;

        for(Route route : routes){
            boolean isMatch = route.isMatch(runtimeRequest);
            if(isMatch) return route;
        }

        return null;
    }
}
