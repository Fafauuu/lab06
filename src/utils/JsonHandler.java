package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Tour;
import model.Tourist;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private static final Gson gson = new Gson();
    private static final Type tourListType = new TypeToken<ArrayList<Tour>>() {
    }.getType();

    public static String tourToJson(Tour tour){
        return gson.toJson(tour);
    }

    public static Tour jsonToTour(String json){
        return gson.fromJson(json, Tour.class);
    }

    public static String tourListToJson(List<Tour> tours){
        return gson.toJson(tours, tourListType);
    }

    public static String touristToJson(Tourist tourist){
        return gson.toJson(tourist);
    }

    public static Tourist jsonToTourist(String json){
        return gson.fromJson(json, Tourist.class);
    }



}
