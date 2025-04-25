//package com.example.demo.service;
//
////package com.example.demo.StockController;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Value;
//
//@Service
//public class StockApiService {
//	
//
//    @Value("${alphavantage.api.key}") // Add this to your application.properties
//    private String apiKey;
//
//    private final String BASE_URL = "https://www.alphavantage.co/query";
//
//    public String getStockPrice(String symbol) {
//        String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url, String.class);
//    }
//    
//    
//}


package com.example.demo.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class StockApiService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://www.alphavantage.co/query";

    public double getStockPrice(String symbol) {
        String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode priceNode = root.path("Global Quote").path("05. price");

            if (priceNode.isMissingNode()) {
                throw new RuntimeException("Price not found for symbol: " + symbol);
            }

            return priceNode.asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock price", e);
        }
    }

    public String getStockName(String symbol) {
        return symbol.toUpperCase(); // Placeholder
    }
}
