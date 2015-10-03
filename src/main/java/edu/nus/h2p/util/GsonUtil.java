package edu.nus.h2p.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Hao on 3/10/2015.
 * GsonUtil used to create json file
 */
public class GsonUtil {
    private static final Gson gson = new Gson();
    public static void writeObjectToJsonFile(String path, Object obj){
        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(obj);
        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T readObjectToJsonFile(String path, Class<T> clazz){
        // convert java object to JSON format,
        // and returned as JSON formatted string
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(path));

            //convert the json string back to object
            T obj = gson.fromJson(br, clazz);

            System.out.println(obj);
            return clazz.cast(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
