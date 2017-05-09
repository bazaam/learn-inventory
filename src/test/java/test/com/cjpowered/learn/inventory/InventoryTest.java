package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.StockedItem;
import com.cjpowered.learn.inventory.ace.AceInventoryManager;

/*
 * We need to keep items in stock to prevent back orders. See the README.md
 * for the requirements.
 *
 */

public class InventoryTest {

    @Test
    public void whenNoStockItemsDoNotOrder() {
        // given
        final LocalDate today = LocalDate.now();
        final InventoryDatabase db = new DatabaseTemplate(){
        	@Override
        	public List<Item> stockItems() {
        		// TODO Auto-generated method stub
        		return Collections.emptyList();
        	}
        };
        final InventoryManager im = new AceInventoryManager(db);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void orderEnoughItems()   {
    	// given
        final int onHand = 7;
        final int need = 12;
        final StockedItem item = new StockedItem(need);
    	final LocalDate today = LocalDate.now();
    	final InventoryDatabase db = new DatabaseTemplate(){
    		@Override
    		public List<Item> stockItems() {
    			// TODO Auto-generated method stub
    		return Collections.singletonList(item);
    		}
    		@Override
    		public int onHand(Item item){
    			return onHand;
    		}
    	};
        final InventoryManager im = new AceInventoryManager(db);
        
    	
    	// when
        final List<Order> actual = im.getOrders(today);
    	
    	// then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(need - onHand, actual.get(0).quantity);
              
    }
    
    @Test
    public void orderEnoughItemsAgain()   {
    	// given
        final int onHand = 18;
        final int need = 29;
        final StockedItem item = new StockedItem(need);
    	final LocalDate today = LocalDate.now();
    	final InventoryDatabase db = new DatabaseTemplate(){
    		@Override
    		public List<Item> stockItems() {
    			// TODO Auto-generated method stub
    		return Collections.singletonList(item);
    		}
    		@Override
    		public int onHand(Item item){
    			return onHand;
    		}
    	};
        final InventoryManager im = new AceInventoryManager(db);
        
    	
    	// when
        final List<Order> actual = im.getOrders(today);
    	
    	// then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(need - onHand, actual.get(0).quantity);
              
    }
    
    @Test
    public void whenOverstockedDoNotOrder()   {
    	// given
        final int onHand = 26;
        final int need = 5;
        final StockedItem item = new StockedItem(need);
    	final LocalDate today = LocalDate.now();
    	final InventoryDatabase db = new DatabaseTemplate(){
    		@Override
    		public List<Item> stockItems() {
    			// TODO Auto-generated method stub
    		return Collections.singletonList(item);
    		}
    		@Override
    		public int onHand(Item item){
    			return onHand;
    		}
    	};
        final InventoryManager im = new AceInventoryManager(db);
        
    	
    	// when
        final List<Order> actual = im.getOrders(today);
    	
    	// then
        assertTrue(actual.isEmpty());
              
    }
    @Test
    public void orderAllNeededItems(){
    	// given
    	final int onHandItem1 = 10;
    	final int needItem1 = 20;
    	final int onHandItem2 = 7;
    	final int needItem2 = 83;
    	final StockedItem item1 = new StockedItem(needItem1);
    	final StockedItem item2 = new StockedItem(needItem2);
    	final LocalDate today = LocalDate.now();
    	final InventoryDatabase db = new DatabaseTemplate(){
    		@Override
    		public List<Item> stockItems() {
    			// TODO Auto-generated method stub
    		return Arrays.asList(item1, item2);
    		}
    		@Override
    		public int onHand(Item item){
    			if (item.equals(item1))
    				return onHandItem1;
    			if (item.equals(item2))
    				return onHandItem2;
    			return -99;
    		}
    	};
        final InventoryManager im = new AceInventoryManager(db);

    	
    	// when
    	final List<Order> actual = im.getOrders(today);
    	
    	// then
    	assertEquals(2, actual.size());
    	assertEquals(needItem1 - onHandItem1, actual.get(0).quantity);
    	assertEquals(needItem2 - onHandItem2, actual.get(1).quantity);
    	assertEquals(item1, actual.get(0).item);
    	assertEquals(item2, actual.get(1).item);
    }
}
