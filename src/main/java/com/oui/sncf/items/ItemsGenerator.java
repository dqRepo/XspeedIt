package com.oui.sncf.items;

import java.util.Random;


public class ItemsGenerator {
	
	private String items;

	public ItemsGenerator() {
	}

	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}
	
	public String generateRndItemsString(){
		StringBuilder stringBuilder = new StringBuilder();
		int randomLenth =(int) (Math.random()*Math.random()*100);
		for (int i =0; i <= randomLenth;i++){
			Random rn = new Random();
			int answer = rn.nextInt(10) + 1;
			stringBuilder.append(answer);
		}
		this.items = stringBuilder.toString();
		return this.items;
	}
	
	

}
