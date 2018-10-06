package com.company;

public class HeaderObject {
    String header;
    String value;
    public HeaderObject(String line){
        String components[] = line.split(":");
        this.header = components[0];
        this.value = components[1];
    }
}
