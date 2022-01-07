package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Tour;
import model.Tourist;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final Gson gson = new Gson();
    private static final Type tourListType = new TypeToken<ArrayList<Tour>>() {
    }.getType();
    private static final Type touristListType = new TypeToken<ArrayList<Tourist>>() {
    }.getType();

    public static List<Tour> readTourList(String path) {
        List<Tour> tours = new ArrayList<>();
        try {
            tours = gson.fromJson(new FileReader(path), tourListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (tours == null) tours = new ArrayList<>();
        return tours;
    }

    public static void saveTourList(String path, List<Tour> tourList) {
        String json = gson.toJson(tourList, tourListType);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Tourist> readTouristList(String path) {
        List<Tourist> tourists = new ArrayList<>();
        try {
            tourists = gson.fromJson(new FileReader(path), touristListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (tourists == null) tourists = new ArrayList<>();
        return tourists;
    }

    public static void saveTouristList(String path, List<Tourist> touristList) {
        String json = gson.toJson(touristList);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
