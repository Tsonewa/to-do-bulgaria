package com.example.todobulgaria.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

//@RestController
public class WeatherRestController {

    private static String API_KEY = "99e6406a5dbd944e77648f68cd84fb42";
    private static String LOCATION = "Sofia";
    private static String urlString = "https://api.openweathermap.org/data/2.5/forecast?q=" + LOCATION + ",BG&appid=" + API_KEY;


}



