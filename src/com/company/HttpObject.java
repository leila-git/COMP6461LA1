package com.company;

import java.util.ArrayList;

public class HttpObject {
    String host;
    String url;
    String urlParamaters;
    String data;
    String typeRequest;
    boolean isVerbose;
    boolean isInline;
    boolean readFromFile;
    boolean writeTofile;
    String fileName;
    ArrayList<String> headers;
    public HttpObject(){
        headers = new ArrayList<String>();
    }

    @Override
    public String toString() {
        return "HttpObject{" +
                "\nhost='" + host + '\'' +
                ", \nurl='" + url + '\'' +
                ", \nurlParamaters='" + urlParamaters + '\'' +
                ", \ndata='" + data + '\'' +
                ", \ntypeRequest='" + typeRequest + '\'' +
                ", \nisVerbose=" + isVerbose +
                ", \nisInline=" + isInline +
                ", \nreadFromFile=" + readFromFile +
                ", \nwriteTofile=" + writeTofile +
                ", \nfileName='" + fileName + '\'' +
                ", \nheaders=" + headers +
                '}';
    }
}
