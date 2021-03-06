package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void whenOverstockedDoNotOrder()   {
    	// given
        final int onHand = 26;
        final int need = 5;
        final boolean isOnSale = false;
        final StockedItem item = new StockedItem(need, isOnSale);
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
    	final boolean isOnSaleItem1 = false;
    	final int onHandItem2 = 7;
    	final int needItem2 = 83;
    	final boolean isOnSaleItem2 = false;
    	final StockedItem item1 = new StockedItem(needItem1, isOnSaleItem1);
    	final StockedItem item2 = new StockedItem(needItem2, isOnSaleItem2);
    	final LocalDate today = LocalDate.now();
    	final HashMap<Item, Integer> fakeData = new HashMap<>();
    	fakeData.put(item1, onHandItem1);
    	fakeData.put(item2, onHandItem2);
    	final InventoryDatabase db = new FakeDatabase(fakeData);
        final InventoryManager im = new AceInventoryManager(db);

    	
    	// when
    	final List<Order> actual = im.getOrders(today);
    	
    	// then
    	Set<Order> expected = new HashSet<>();
    	expected.add(new Order(item1, needItem1 - onHandItem1));
    	expected.add(new Order(item2, needItem2 - onHandItem2));
    	assertEquals(expected, new HashSet<>(actual));
    }
    @Test
    public void orderExtraWhenOnSale(){
    	//given
    	final int onHandItem1 = 10;
    	final int needItem1 = 20;
    	final boolean isOnSaleItem1 = true;
    	final int onHandItem2 = 7;
    	final int needItem2 = 83;
    	final boolean isOnSaleItem2 = false;
    	final StockedItem item1 = new StockedItem(needItem1, isOnSaleItem1);
    	final StockedItem item2 = new StockedItem(needItem2, isOnSaleItem2);
    	final LocalDate today = LocalDate.now();
    	final HashMap<Item, Integer> fakeData = new HashMap<>();
    	fakeData.put(item1, onHandItem1);
    	fakeData.put(item2, onHandItem2);
    	final InventoryDatabase db = new FakeDatabase(fakeData);
        final InventoryManager im = new AceInventoryManager(db);
    	
    	//when
        final List<Order> actual = im.getOrders(today);
    	
    	//then
        Set<Order> expected = new HashSet<>();
        expected.add(new Order(item1, (needItem1 + 20) - onHandItem1));
        expected.add(new Order(item2, needItem2 - onHandItem2));
        assertEquals(expected, new HashSet<>(actual));
    }
}
