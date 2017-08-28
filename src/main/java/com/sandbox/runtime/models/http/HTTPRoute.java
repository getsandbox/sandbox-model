package com.sandbox.runtime.models.http;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandbox.runtime.models.EngineRequest;
import com.sandbox.runtime.models.Route;
import com.sandbox.runtime.models.RuntimeRequest;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.jaxrs.model.ExactMatchURITemplate;

/**
 * Created by nickhoughton on 3/08/2014.
 */
public class HTTPRoute extends Route {

    String method;
    String path;

    @JsonIgnore
    ExactMatchURITemplate uriTemplate;

    public HTTPRoute() {
        super();
    }

    public HTTPRoute(String method, String path, Map<String, String> properties) {
        super();

        if(path.equals("*") || path.equals("/*")){
            //replace wildcard with a JAXRS friendly syntax
            path = "/{route: .*}";

        } else if(path.contains(":") && !(path.contains("{") && path.contains("}")) ){
            //replace simple :param express routes with JAXRS {param} style ones.
            Matcher matcher = Pattern.compile(":([A-z0-9]+)").matcher(path);
            while (matcher.find()){
                String variable = matcher.group(0);
                path = path.replaceAll(variable, "{" + variable.substring(1) + "}");
            }

        }

        //coalesce varied wildcard method into one
        if(method.equalsIgnoreCase("all") || method.equalsIgnoreCase("*")) method = "ALL";

        this.method = method;
        this.path = path;
        setProperties(properties);
    }

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

    public MultivaluedMap<String, String> extractPathParams(String uri) {
        MultivaluedHashMap pathParams = new MultivaluedHashMap<>();
        process().match(uri, pathParams);
        return pathParams;
    }


    public ExactMatchURITemplate getUriTemplate() {
        return uriTemplate;
    }

    protected void setUriTemplate(ExactMatchURITemplate uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    public ExactMatchURITemplate process(){
        if(uriTemplate != null) return uriTemplate;

        uriTemplate = new ExactMatchURITemplate(getPath());
        return uriTemplate;
    }

    @JsonIgnore
    public boolean isWildcardMethod(String compareMethod){
        return compareMethod != null && (compareMethod.equals("*") || compareMethod.equalsIgnoreCase("all"));
    }

    public boolean matchesMethod(String method){
        if(method == null || getMethod() == null) return false;

        //check both sides of the comparison for wildcard methods
        if(isWildcardMethod(getMethod()) || isWildcardMethod(method)) {

            return true;
        }else{
            return getMethod().equalsIgnoreCase(method);
        }
    }

    public boolean matchesExactPath(String path) {
        return getPath().equals(path);
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

    @JsonIgnore
    @Override
    public String getProcessingKey() {
        return process().getLiteralChars();
    }

    @JsonIgnore
    @Override
    public String getDisplayKey() {
        return getPath().concat(getMethod());
    }

    @Override
    public boolean isMatch(RuntimeRequest runtimeRequest) {
        if(runtimeRequest instanceof HttpRuntimeRequest){
            HttpRuntimeRequest httpReq = (HttpRuntimeRequest) runtimeRequest;

            return isMatch(httpReq.getMethod(), httpReq.getUrl(), httpReq.getProperties());
        }else{
            return false;
        }
    }

    public boolean isMatch(Route otherRoute) {
        if(otherRoute instanceof HTTPRoute){
            HTTPRoute otherHttpRoute = (HTTPRoute) otherRoute;
            return isMatch(otherHttpRoute.getMethod(), otherHttpRoute.getPath(), otherRoute.getProperties());
        }else{
            return false;
        }
    }

    //matches based on actual url /blah/1 -> /blah/{smth}
    public boolean isMatch(String method, String url, Map<String, String> properties){
        if(method == null || url == null || getMethod() == null || getPath() == null) return false;

        //if method isnt right, skip!
        if(!matchesMethod(method)) return false;
        //if headers arent right, skip!
        if(!matchesProperties(properties)) return false;

        //if paths are exactly the same then match
        if (matchesExactPath(url)) return true;

        //method matches, so continue..
        ExactMatchURITemplate template = process();

        //if we have a match, then set it as the best match, because we could match more than one, we want the BEST match.. which i think should be the one with the shortest 'finalMatchGroup'..
        if(template.match(url)) {
            return true;
        }else{
            return false;
        }
    }


    //matches based on uncompiled path /blah/{smth}
    public boolean isUncompiledMatch(EngineRequest req){
        if(req instanceof HTTPRequest){
            HTTPRequest httpReq = (HTTPRequest) req;
            return matchesMethod(httpReq.method()) && httpReq.path().equalsIgnoreCase(path) && matchesProperties(req.getProperties());
        }else{
            return false;
        }
    }

    public boolean isUncompiledMatch(String method, String path, Map<String, String> properties){
        return matchesMethod(method) && matchesExactPath(path) && matchesProperties(properties);
    }

}
