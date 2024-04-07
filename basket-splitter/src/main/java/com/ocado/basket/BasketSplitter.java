package com.ocado.basket;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketSplitter 
{
    private JSONObject config;

    public BasketSplitter(String configFilePath)
    {
        try 
        {
            config = load_config(configFilePath);
        } 
        catch (JSONException | IOException e) 
        {
            e.printStackTrace();
        }

    }

    public Map<String, List<String>> split(List<String> items) 
    {
        Map<String, List<String>> subsets = subsets(items, config);
        Map<String, List<String>> cover = new HashMap<>();
        List<String> uncovered = new ArrayList<>(items);
        String bestDelivery;
        List<String> bestSubset;
        int bestSubsetSize;
        List<String> subset;
    
        while (!uncovered.isEmpty()) 
        {
            bestDelivery = null;
            bestSubset = null;
            bestSubsetSize = 0;
            
            for (Map.Entry<String, List<String>> entry : subsets.entrySet()) 
            {
                subset = new ArrayList<>(entry.getValue());
                subset.retainAll(uncovered);
    
                if (subset.size() > bestSubsetSize) 
                {
                    bestDelivery = entry.getKey();
                    bestSubset = subset;
                    bestSubsetSize = subset.size();
                }
            }
    
            if (bestDelivery != null && bestSubset != null) 
            {
                cover.put(bestDelivery, bestSubset);
                uncovered.removeAll(bestSubset);
            }
        }
    
        return cover;
    }
    

    private static JSONObject load_config(String configFilePath) throws IOException, JSONException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) 
        {
            jsonString.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(jsonString.toString());

        return jsonObject;
    }

    public List<String> load_basket(String basketFilePath) throws FileNotFoundException, IOException
    {
        List<String> basket = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(basketFilePath));
        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) 
        {
            jsonString.append(line);
        }
        reader.close();

        JSONArray jsonArray = new JSONArray(jsonString.toString());

        for (int i = 0; i < jsonArray.length(); i++) 
        {
            basket.add(jsonArray.getString(i));
        }
        
        return basket;
    }

    public void saveBasket(Map<String, List<String>> splittedItems)
    {
        JSONObject splitted = new JSONObject(splittedItems);

        try 
        {
            Files.write(Paths.get("output.json"), splitted.toString(4).getBytes());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static Map<String, List<String>> subsets(List<String> items, JSONObject config)
    {
        Map<String, List<String>> deliveryGroups = new HashMap<>();
        JSONArray deliveryMethods;
        List<String> newGroup;

        for(String key : config.keySet()) 
        {
            deliveryMethods = config.getJSONArray(key);
            for(int i = 0; i < deliveryMethods.length(); i++) 
            {
                if (deliveryGroups.containsKey(deliveryMethods.getString(i)))
                {
                    deliveryGroups.get(deliveryMethods.getString(i)).add(key);
                }
                else
                {
                    newGroup = new ArrayList<>();
                    newGroup.add(key);
                    deliveryGroups.put(deliveryMethods.getString(i), newGroup);
                }
            }
        }

        return deliveryGroups;
    }
    
}
