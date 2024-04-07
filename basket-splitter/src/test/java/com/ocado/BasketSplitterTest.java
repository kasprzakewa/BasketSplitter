package com.ocado;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.ocado.basket.BasketSplitter;

public class BasketSplitterTest 
{

    private BasketSplitter basketSplitter;
    private Map<String, List<String>> splitItems;
    private List<String> items;

    @Before
    public void setUp() throws FileNotFoundException, IOException 
    {
        basketSplitter = new BasketSplitter("/home/ewa/Desktop/jobs/ocado/java/basket_splitter_git/example_config.json");
        items = basketSplitter.loadBasket("/home/ewa/Desktop/jobs/ocado/java/basket_splitter_git/example_basket.json");
        splitItems = basketSplitter.split(items);
    }

    @Test
    public void testSplit() throws IOException 
    {
        assertEquals(2, splitItems.size());
        assertEquals(Arrays.asList("Cold Beer (330ml)", "Steak (300g)", "AA Battery (4 Pcs.)", "Carrots (1kg)"), splitItems.get("Express Delivery"));
        assertEquals(Arrays.asList("Garden Chair", "Espresso Machine"), splitItems.get("Courier"));
    }

    @Test
    public void testLoadBasket() throws IOException 
    {
        assertEquals(6, items.size());
        assertEquals("Steak (300g)", items.get(0));
        assertEquals("Carrots (1kg)", items.get(1));
        assertEquals("AA Battery (4 Pcs.)", items.get(2));
        assertEquals("Espresso Machine", items.get(3));
        assertEquals("Garden Chair", items.get(4));
        assertEquals("Cold Beer (330ml)", items.get(5));
    }

    @Test
    public void testSaveBasket() throws IOException
    {
        String expectedJSON = "{\"Courier\":[\"Garden Chair\",\"Espresso Machine\"],\"Express Delivery\":[\"Cold Beer (330ml)\",\"Steak (300g)\",\"AA Battery (4 Pcs.)\",\"Carrots (1kg)\"]}";
        String actualJSON = new String(Files.readAllBytes(Paths.get("/home/ewa/Desktop/jobs/ocado/java/basket_splitter_git/example_output.json")));
        JSONAssert.assertEquals(expectedJSON, actualJSON, true);
    }
}
