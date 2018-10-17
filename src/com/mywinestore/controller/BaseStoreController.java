package com.mywinestore.controller;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class BaseStoreController {
	
	public URI getBaseURI() {
        return UriBuilder.fromUri("https://lcboapi.com").build();
    }
	
	public WebTarget getWebTarget() {
		ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        return target;
	}

}
