package com.example.SE1719_SE172258_ducthse172258.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class UnixTimestampDateAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getTime() / 1000);
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in != null && in.peek() != null) {
            try {
                long timestamp = in.nextLong();
                return new Date(timestamp * 1000);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
