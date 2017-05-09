package com.cjpowered.learn.inventory;

public class StockedItem implements Item {
	private final int needed;
	
	public StockedItem(int newLevel){
		needed = newLevel;
	}
	
	@Override
	public int needed(){
		return needed;
	}
}
