package com.mywinestore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mywinestore.bo.Item;

/*
 * 01. Mediates the communication between the LCBO's REST API and the client class(WineStoreClient)
 * 02. Uses the JAX-RS Client API(Jersey) to make requests to 3rd party services
 * 03. Uses Jackson Streaming API to parse the JSON string 
 */
public class WineStoreController extends BaseStoreController {
	
	public static final String AUTHORIZATION_KEY = "Token MDo3NzVhOWZlMC1kMTQyLTExZTgtODVlYy0wYjMzYjRjZDA0Yjk6RVJmMEhDaUY0NGpIYjhMMUoyQ3BmV2Q0YzV0RkYyQ05McUlJ";
	private ArrayList<Item> items = null; 
	
	public void loadData(int page) {
        WebTarget target = getWebTarget();        
        String inventory = target.path("stores").
        		path("643").
        		path("products").
        		queryParam("per_page", "100").
        		queryParam("page", String.valueOf(page)).
                request().
                accept(MediaType.APPLICATION_JSON).
                header("Authorization", AUTHORIZATION_KEY).
                get(Response.class).
                readEntity(String.class);

        buildItems(inventory);
    }
	
	
	public void buildItems(String inventory) {
		//inventory = "{\"name\":\"Tom\",\"age\":25,\"custom\":{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland1\",\"15th avenue\"]},\"address\":[\"Poland\",\"5th avenue\"]}";
		items = new ArrayList<>();
		Item item = null;
		try {
			JsonParser jsonParser = new JsonFactory().createParser(inventory);
			while(jsonParser.nextToken() != JsonToken.END_ARRAY){
				String name = jsonParser.getCurrentName();
				if("id".equals(name)){
					jsonParser.nextToken();
					item = new Item();
					item.setId(jsonParser.getIntValue());
					items.add(item);
				} else if("origin".equals(name)){
					jsonParser.nextToken();
					if(item != null) {
						item.setCountry(jsonParser.getText());
					}
				} else if("producer_name".equals(name)){
					jsonParser.nextToken();
					if(item != null) {
						item.setProducer(jsonParser.getText());
					}
				} else if("has_vintages_corner".equals(name)){
					jsonParser.nextToken();
					if(item != null) {
						item.setVintage(jsonParser.getText());
					}
				} else if("varietal".equals(name)){
					jsonParser.nextToken();
					if(item != null) {
						item.setVarietal(jsonParser.getText());
					}
				} else if("price_in_cents".equals(name)){
					jsonParser.nextToken();
					int priceInCents = jsonParser.getIntValue();
					if(item != null) {
						item.setPrice(BigDecimal.valueOf(priceInCents/100));
					}
				} else if("primary_category".equals(name)){
					jsonParser.nextToken();
					if(item != null) {
						item.setCategory(jsonParser.getText());
					}
				}
			}
			jsonParser.close();
			//items.forEach(product -> System.out.println(product.getId()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList<Item> getItems() {
		return items != null ? items : new ArrayList<Item>();
	}
}
