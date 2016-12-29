package com.sandbox.runtime.converters;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by nickhoughton on 13/03/2015.
 */
public abstract class RequestConverter {

    Pattern jsonPattern = Pattern.compile("^application\\/([\\w!#\\$%&\\*`\\-\\.\\^~]*\\+)?json.*$", Pattern.CASE_INSENSITIVE);
    Pattern xmlPattern = Pattern.compile("(text\\/xml|application\\/([\\w!#\\$%&\\*`\\-\\.\\^~]+\\+)?xml).*$", Pattern.CASE_INSENSITIVE);

    public Pattern getJsonPattern() {
        return jsonPattern;
    }

    public Pattern getXmlPattern() {
        return xmlPattern;
    }

    public static Map<String,String> getHeadersAsMap(HttpServletRequest request){
        Map<String,String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        Enumeration<String> keys = request.getHeaderNames();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();

            Enumeration<String> values = request.getHeaders(key);

            while(values.hasMoreElements()){
                String value = values.nextElement();
                //if the SOAPAction is wrapped in quotes, remove them to simplify matching.
                if(key.equalsIgnoreCase("SOAPAction") && value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length()-1);
                headers.put(key, value);
            }

        }

        return headers;
    }

    public static List<String> getAcceptedHeadersFromHeaders(Enumeration<String> acceptedHeaders){
        List<String> accepted = new ArrayList<String>();
        if(acceptedHeaders == null) return accepted;

        while(acceptedHeaders.hasMoreElements()){
            String acceptedValue = acceptedHeaders.nextElement();
            if(acceptedValue.indexOf(",")!=-1){
                for(String subValue : acceptedValue.split(",")){
                    accepted.add(subValue);
                }
            }else{
                accepted.add(acceptedValue);
            }

        }

        return accepted;

    }



}
