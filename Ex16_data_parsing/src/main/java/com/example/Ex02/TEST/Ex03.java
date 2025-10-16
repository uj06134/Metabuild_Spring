package com.example.Ex02.TEST;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Ex03 {
    public static void main(String[] args) throws IOException, ParseException {
        Path currentPath =  Paths.get(".");
        String path = currentPath.toAbsolutePath().toString();
        System.out.println("path: " + path);

        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("file\\test1.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader); // 속성명과 속성값을 분석

        String name = (String)jsonObject.get("name");
        long id = (long)jsonObject.get("id");
        long price = (long)jsonObject.get("price");

        System.out.println("name:" + name);
        System.out.println("id:" + id);
        System.out.println("price:" + price);

        Set keySet = jsonObject.keySet();
        for (Object key : keySet) {
            System.out.println("key: " + key + ", value: " + jsonObject.get(key));
        }

        System.out.println("---------------------------------");
        JSONParser parser2 = new JSONParser();
        Reader reader2 = new FileReader("file\\test2.json");
        Object jsonObject2 = parser2.parse(reader2);

        JSONArray jsonArr = (JSONArray) jsonObject2;

        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jObject = (JSONObject)jsonArr.get(i);
            Set keySet2 = jObject.keySet();
            for (Object key : keySet2) {
                System.out.println(key + ": " + jObject.get(key));
            }
        }

        System.out.println("---------------------------------");
        JSONParser parser3 = new JSONParser();
        Reader reader3 = new FileReader("file\\test3.json");
        JSONObject jsonObject3 = (JSONObject)parser2.parse(reader3);

        JSONArray jsonArr3 = (JSONArray) jsonObject3.get("employee");
        for (int i = 0; i < jsonArr3.size(); i++) {
            JSONObject jObject = (JSONObject) jsonArr3.get(i);
            Set keySet3 = jObject.keySet();
            for (Object key : keySet3) {
                System.out.println(key + ": " + jObject.get(key));
            }

        }


    }
}
