import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ocado.basket.BasketSplitter;

public class ExampleApp 
{
    public static void main(String[] args)
    {
        System.out.print("Enter config file path: ");
        String configFilePath = System.console().readLine();
        BasketSplitter splitter = new  BasketSplitter(configFilePath);

        List<String> items;
        System.out.print("Enter basket file path: ");
        String basketFilePath = System.console().readLine();

        try 
        {
            items = splitter.load_basket(basketFilePath);
            Map<String, List<String>> splittedItems = splitter.split(items);
            splitter.saveBasket(splittedItems);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 

        System.out.println("Basket has been split and saved to file output.json.");
    } 
}
