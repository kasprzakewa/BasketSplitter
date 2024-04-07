/**
 * The BasketSplitter class is responsible for intelligently distributing the deliveries
 * of products from the basket in an online store, aiming to minimize the number of deliveries.
 */
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
    // Stores the configuration read from the JSON file
    private JSONObject config;

    /**
     * Constructs a BasketSplitter object with the specified configuration file.
     *
     * @param configFilePath The path to the configuration file.
     */
    public BasketSplitter(String configFilePath) 
    {
        try 
        {
            // Load the configuration from the specified file
            config = loadConfig(configFilePath);
        } 
        catch (JSONException | IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Splits the items in the basket into subsets based on delivery methods.
     *
     * @param items The list of items in the basket.
     * @return A map where keys are delivery methods and values are lists of items to be delivered using that method.
     */
    public Map<String, List<String>> split(List<String> items) 
    {
        // Generate subsets based on delivery methods
        Map<String, List<String>> subsets = subsets(items, config);
        // Map to store the cover of deliveries
        Map<String, List<String>> cover = new HashMap<>();
        // List to track uncovered items
        List<String> uncovered = new ArrayList<>(items);
        // Variables to track the best delivery method and subset
        String bestDelivery;
        List<String> bestSubset;
        int bestSubsetSize;
        List<String> subset;

        // Iterate until all items are covered
        while (!uncovered.isEmpty()) 
        {
            bestDelivery = null;
            bestSubset = null;
            bestSubsetSize = 0;
            
            // Find the best delivery method for the remaining uncovered items
            for (Map.Entry<String, List<String>> entry : subsets.entrySet()) 
            {
                subset = new ArrayList<>(entry.getValue());
                subset.retainAll(uncovered);
    
                // Update the best delivery method if a better one is found
                if (subset.size() > bestSubsetSize) 
                {
                    bestDelivery = entry.getKey();
                    bestSubset = subset;
                    bestSubsetSize = subset.size();
                }
            }
    
            // Add the best delivery and its subset to the cover
            if (bestDelivery != null && bestSubset != null) 
            {
                cover.put(bestDelivery, bestSubset);
                // Remove covered items from the list of uncovered items
                uncovered.removeAll(bestSubset);
            }
        }
    
        return cover;
    }
    

    /**
     * Loads the configuration from a JSON file.
     *
     * @param configFilePath The path to the configuration file.
     * @return The JSONObject representing the configuration.
     * @throws IOException If an I/O error occurs.
     * @throws JSONException If the JSON is invalid.
     */
    private JSONObject loadConfig(String configFilePath) throws IOException, JSONException 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) 
        {
            StringBuilder jsonString = new StringBuilder();
            String line;

            // Read the JSON file line by line
            while ((line = reader.readLine()) != null) 
            {
                jsonString.append(line);
            }

            // Parse the JSON string to create a JSONObject
            return new JSONObject(jsonString.toString());
        }
    }

    /**
     * Loads the items from a JSON file representing the basket.
     *
     * @param basketFilePath The path to the basket JSON file.
     * @return The list of items in the basket.
     * @throws FileNotFoundException If the file is not found.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> loadBasket(String basketFilePath) throws FileNotFoundException, IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(basketFilePath))) 
        {
            StringBuilder jsonString = new StringBuilder();
            String line;

            // Read the JSON file line by line
            while ((line = reader.readLine()) != null) 
            {
                jsonString.append(line);
            }

            // Parse the JSON array to create a list of items
            JSONArray jsonArray = new JSONArray(jsonString.toString());
            List<String> basket = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) 
            {
                basket.add(jsonArray.getString(i));
            }
            
            return basket;
        }
    }

    /**
     * Generates subsets of items based on the delivery configuration.
     *
     * @param items The list of items to be split.
     * @param config The JSON object representing the delivery configuration.
     * @return A map where keys are delivery methods and values are lists of items to be delivered using that method.
     */
    private Map<String, List<String>> subsets(List<String> items, JSONObject config)
    {
        // Map to store delivery groups
        Map<String, List<String>> deliveryGroups = new HashMap<>();
        JSONArray deliveryMethods;

        // Iterate over each item in the configuration
        for(String key : config.keySet()) 
        {
            // Get the array of delivery methods for the current item
            deliveryMethods = config.getJSONArray(key);
            // Add the item to the corresponding delivery groups
            for(int i = 0; i < deliveryMethods.length(); i++) 
            {
                String deliveryMethod = deliveryMethods.getString(i);
                deliveryGroups.computeIfAbsent(deliveryMethod, k -> new ArrayList<>()).add(key);
            }
        }

        return deliveryGroups;
    }

    /**
     * Saves the split items to a JSON file.
     *
     * @param splitItems The map containing the split items.
     */
    public void saveBasket(Map<String, List<String>> splitItems)
    {
        JSONObject split = new JSONObject(splitItems);

        try 
        {
            // Write the JSON object to a file
            Files.write(Paths.get("output.json"), split.toString(4).getBytes());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }    
}
