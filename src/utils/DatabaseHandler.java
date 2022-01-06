package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Tour;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final Gson gson = new Gson();
    private static final Type toursListType = new TypeToken<ArrayList<Tour>>() {
    }.getType();

    public static List<Tour> readTourList(String path) {
        List<Tour> tours = new ArrayList<>();
        try {
            tours = gson.fromJson(new FileReader(path), toursListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static void saveTourList(String path, List<Tour> ingredientList) {
        String json = gson.toJson(ingredientList);

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
