package com.example.Ex02.TEST;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Ex04 {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("file\\서울시 공공도서관 현황정보3_ANSI.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        String searchKey = "code_value";
        String getKey = "lbrry_name";

        JSONArray data = (JSONArray) jsonObject.get("DATA");
        for (int i = 0; i < data.size(); i++) {
            JSONObject object = (JSONObject) data.get(i);

            if (object.get(searchKey).equals("강남구")) {
                System.out.println(object.get(getKey));
            }
        }
    }
}
