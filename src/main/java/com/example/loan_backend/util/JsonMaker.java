package com.example.loan_backend.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class JsonMaker {
    private StringWriter writer = new StringWriter();
    private JsonGenerator json;

    public JsonMaker() throws IOException {
        json = new JsonFactory().createGenerator(writer);
        json.writeStartObject();
    }

    public JsonMaker add(String field, String val) throws IOException {
        json.writeStringField(field, val);
        return this;
    }

    public JsonMaker add(String field, double val) throws IOException {
        json.writeNumberField(field, val);
        return this;
    }

    public String build() throws IOException {
        json.writeEndObject();
        json.close();
        return writer.toString();
    }

}
