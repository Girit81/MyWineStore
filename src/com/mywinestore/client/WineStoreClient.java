package com.mywinestore.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;

import com.mywinestore.bo.Item;
import com.mywinestore.bo.User;
import com.mywinestore.controller.WineStoreController;

/*
 * 01. Command-line application that interacts with customers
 */
public class WineStoreClient {
	
	private static final String GREATER_THAN_RULE = "wines>$20";
	private static final String LESS_THAN_RULE = "wines<$20";
	
	public static void main(String [] arg) {
		WineStoreController controller = new WineStoreController();		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String thisLine = null;
		    System.out.print("Please Enter Username:");
		    String username = br.readLine();
		    System.out.print("Please Enter Password:");
		    String password = br.readLine();
		    
		    WineStoreClient client = new WineStoreClient();
		    if(client.isValidUser(username, password)) {
		    	User user = client.refreshUser(username);
		    	int pageCount = 1;
		    	loadProducts(controller, client, user, pageCount++);
		    	
		    	while ((thisLine = br.readLine()) != null) {
		    		String command = thisLine;
		    	    if(command.equals("quit")) {
		    	      break;
		    	    }	    	    
		    	    if(command.equals("showmore")) {
				    	loadProducts(controller, client, user, pageCount++);
		    	    }
		    	}  	
		    } else {
		    	System.out.println("Please check the username/password");
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

	private static int loadProducts(WineStoreController controller, WineStoreClient client, User user, int pageCount) {
		controller.loadData(pageCount++);
		List<Item> allItems = controller.getItems();	    	
		if(user != null && user.getUserRule().equals(GREATER_THAN_RULE)) {
			// Filtering the data using Java 8's Lambda expression
			List<Item> premiumItems = allItems.stream().
					filter(item -> item.getPrice().doubleValue() > 20 && item.getCategory().equals("Wine")).
					collect(Collectors.toList());
			client.print(premiumItems);
		} else if (user != null && user.getUserRule().equals(LESS_THAN_RULE)) {
			List<Item> affordableItems = allItems.stream().
					filter(item -> item.getPrice().doubleValue() < 20 && item.getCategory().equals("Wine")).
					collect(Collectors.toList());
			client.print(affordableItems);
		}
		return pageCount;
	}
	
	private HashMap<String,User> makeUsers() {
		HashMap<String, User> pwByUserName = new HashMap<>();
		pwByUserName.put("Grant", new User("Grant", "12345", "wines>$20"));
		pwByUserName.put("David", new User("David", "abcde", "wines<$20"));
		return pwByUserName;
	}
	
	private boolean isValidUser(String userName, String pw) {
		User user = refreshUser(userName);		
		return user != null && user.getPassword().equals(pw);
	}
	
	private User refreshUser(String userName) {
		HashMap<String, User> pwByUserNam = makeUsers();
		User user = pwByUserNam.get(userName);
		return user;
	}
	
	public void print(List<Item> items) {
		items.forEach(item ->  {
			System.out.println("Country: " + item.getCountry() + ", " +
							   "Producer: " + item.getProducer() + ", " +
							   "Price: $" + item.getPrice() + ", " +
							   "Vintage: " + item.getVintage() + ", " +
							   "Varietal: " + item.getVarietal());
		});
	}
}
