package com.oui.sncf.items;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Classe representant l'objet Boite
 * @author Quentin
 *
 */
@Component
public class ItemBox {
	
	private List<Integer> itemsInBox;
	
	
	/**
	 * ItemBox constructor
	 */
	
	public ItemBox() {
		this.itemsInBox = new ArrayList<>();
	}


	/**
	 * @return the itemsInBox
	 */
	public List<Integer> getItemsInBox() {
		return itemsInBox;
	}


	/**
	 * @param itemsInBox the itemsInBox to set
	 */
	public void setItemsInBox(List<Integer> itemsInBox) {
		this.itemsInBox = itemsInBox;
	}


	/**
	 * ajoute un article au carton.
	 * @param item
	 */
	public void add(Integer item){
		this.itemsInBox.add(item);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.itemsInBox.toString();
	}

}
