package com.cjpowered.learn.inventory;

public class StockedItem implements Item {
	private final int needed;
	private final boolean isOnSale;
	
	public StockedItem(int newLevel, boolean isOnSale){
		needed = newLevel;
		this.isOnSale = isOnSale;
	}
	
	@Override
	public int needed(){
		return needed;
	}
	public boolean isOnSale(){
		return isOnSale;
	}
}
