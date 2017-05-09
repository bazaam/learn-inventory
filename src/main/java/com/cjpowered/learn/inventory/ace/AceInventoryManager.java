package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;

public final class AceInventoryManager implements InventoryManager {
	
	private final InventoryDatabase database;
	
	public AceInventoryManager(InventoryDatabase database) {
		this.database = database;
	}

     @Override
    public List<Order> getOrders(final LocalDate today) {
    	 
    	 final List<Item> items = database.stockItems();
    	 
    	 final List<Order> orders = new ArrayList<>();
    	 for(Item item : items) {
    		 Order order = new Order(item, 5);
    		 orders.add(order);
    		 
    	 }
    	 
    	 return orders;
    	 
        
    }

}
