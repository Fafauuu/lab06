package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Guide;
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
    private static final Type guideListType = new TypeToken<ArrayList<Guide>>() {
    }.getType();

    public synchronized static List<Tour> readTourList(String path) {
        List<Tour> tours = new ArrayList<>();
        try {
            tours = gson.fromJson(new FileReader(path), tourListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (tours == null) tours = new ArrayList<>();
        return tours;
    }

    public synchronized static void saveTourList(String path, List<Tour> tourList) {
        String json = gson.toJson(tourList, tourListType);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<Tourist> readTouristList(String path) {
        List<Tourist> tourists = new ArrayList<>();
        try {
            tourists = gson.fromJson(new FileReader(path), touristListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (tourists == null) tourists = new ArrayList<>();
        return tourists;
    }

    public synchronized static void saveTouristList(String path, List<Tourist> touristList) {
        String json = gson.toJson(touristList);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<Guide> readGuideList(String path) {
        List<Guide> guides = new ArrayList<>();
        try {
            guides = gson.fromJson(new FileReader(path), guideListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (guides == null) guides = new ArrayList<>();
        return guides;
    }

    public synchronized static void saveGuideList(String path, List<Guide> guideList) {
        String json = gson.toJson(guideList);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
