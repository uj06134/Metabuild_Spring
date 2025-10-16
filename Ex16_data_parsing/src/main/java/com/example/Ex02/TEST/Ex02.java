package com.example.Ex02.TEST;

import java.io.*;

public class Ex02 {
    public static void main(String[] args) {
        File file = new File("file\\서울시 공공도서관 현황정보.csv");
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                String[] arr = line.split(",");
                // 강남구
                if(arr[3].equals("\"강남구\"")){
                    // System.out.println(line);
                }
            }
            br.close();

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                String[] arr = line.split(",");
                if(arr[1].contains("어린이도서관")){
                    System.out.println(line);
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
