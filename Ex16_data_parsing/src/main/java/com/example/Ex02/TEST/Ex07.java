package com.example.Ex02.TEST;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Ex07 {
    public static void main(String[] args) throws IOException, ParseException {
        // http://openapi.seoul.go.kr:8088/(인증키)/json/tbCycleStationUseMonthInfo/1/5/202208
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("59506c474d756a303835756b594a62", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("tbCycleStationUseMonthInfo", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("50", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("202208", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        JSONObject info = (JSONObject) jsonObject.get("stationUseMonthInfo");
        JSONArray rowArray = (JSONArray) info.get("row");

        for (int i=0; i < rowArray.size(); i++){
            JSONObject station = (JSONObject) rowArray.get(i);
            int rentCnt = Integer.parseInt((String) station.get("rentCnt"));

            if (rentCnt >= 3000) {
                System.out.println(station.get("stationName") + ":"+ rentCnt);
            }
        }
    }
}
