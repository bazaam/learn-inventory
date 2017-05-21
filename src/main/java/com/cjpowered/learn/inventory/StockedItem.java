package com.cjpowered.learn.inventory;

public class StockedItem implements Item {
	private final int baseNeeded;
	private final boolean isOnSale;
	
	public StockedItem(int baseNeeded, boolean isOnSale){
		this.baseNeeded = baseNeeded;
		this.isOnSale = isOnSale;
	}
	
	@Override
	public int needed(){
		if (isOnSale){
			return baseNeeded + 20;
		}
		return baseNeeded;
	}
	public boolean isOnSale(){
		return isOnSale;
	}
}
