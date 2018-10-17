package com.mywinestore.bo;

import java.math.BigDecimal;

/*
 * 01. Each instance of Item class is a placeholder for LCBO's Product
 */
public class Item {
	
	private int id;				//id
	private String country; 	//origin
	private String producer; 	//producer_name
    private String vintage;		//has_vintages_corner
    private String varietal;	//varietal	
	private BigDecimal price; 	//price_in_cents
	private String category; 	//primary_category
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public String getVintage() {
		return vintage;
	}
	
	public void setVintage(String vintage) {
		this.vintage = vintage;
	}
	
	public String getVarietal() {
		return varietal;
	}
	
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
