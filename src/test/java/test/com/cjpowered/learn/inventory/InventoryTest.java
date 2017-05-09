package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
        final StockedItem item = new StockedItem();
    	final LocalDate today = LocalDate.now();
    	final InventoryDatabase db = new DatabaseTemplate(){
    		@Override
    		public List<Item> stockItems() {
    			// TODO Auto-generated method stub
    		return Collections.singletonList(item);
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
}
