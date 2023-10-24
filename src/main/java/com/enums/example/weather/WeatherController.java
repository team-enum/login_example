package com.enums.example.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController 
@Controller
public class WeatherController {
    private String key = "7af5d7cd1e11fdb585f994cf20027309";

    // 1. 위도와 경도를 @pathVariable -> 국가만 검색됨 2. @pathVariable city(Seoul)를 기준으로 날씨검색 가능 -> 반복문을 통해 8대 도시로 view 고정 
    @GetMapping("/weather") 
    public String getWeather(Model model) {
        String[] cities = {"Seoul", "Busan", "Incheon", "Daejeon", "Changwon", "Gangneung", "Daegu", "Gwangju"};
        
        for (String city : cities) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                String APIUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key;
                URL url = new URL(APIUrl);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-type", "application/json");

                BufferedReader bufferedReader;

                if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                }

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                bufferedReader.close();
                urlConnection.disconnect();

                // 날씨 정보 model
                model.addAttribute(city, stringBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return "weather";
    }
}
