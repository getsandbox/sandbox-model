package com.sandbox.runtime.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;

public class XMLObjectFactory {

    //keep docuemnt factories around in thread local context
    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private static ThreadLocal<DocumentBuilder> documentBuilderThreadLocal = new ThreadLocal<>();

    public static DocumentBuilder xmlDocumentBuilder() {
        documentBuilderFactory.setValidating(false);
        DocumentBuilder db = null;
        try {
            db = documentBuilderThreadLocal.get();
            if(db == null){
                db = documentBuilderFactory.newDocumentBuilder();
                documentBuilderThreadLocal.set(db);
            }else{
                db.reset();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            db = null;
        }
        return db;
    }

    //keep xpathfactories around in thread local context
    private static ThreadLocal<XPathFactory> xPathBuilderThreadLocal = new ThreadLocal<>();

    public static XPathFactory xPathFactory() {
        XPathFactory xPathFactory = xPathBuilderThreadLocal.get();
        if(xPathFactory == null){
            xPathFactory = XPathFactory.newInstance();
            xPathBuilderThreadLocal.set(xPathFactory);
        }
        return xPathFactory;
    }

}
