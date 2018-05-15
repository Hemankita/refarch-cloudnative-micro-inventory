package application.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import client.InventoryServiceClient;
import client.Item;
import org.eclipse.microprofile.rest.client.inject.RestClient;


public class InventoryRefreshTask extends Thread {

	private static final int INVENTORY_REFRESH_SLEEP_TIME_MS = 60000;

	@Inject
    @RestClient
	private InventoryServiceClient invClient;

	private ElasticSearch elasticSearch = new ElasticSearch() ;
	
	@Inject
	ItemService itemsRepo;
 
	public void run() {
		while (true) {
			try {
				System.out.println("Querying Inventory Service for all items ...");
				final List<Item> allItems = invClient.getAllItems();
				final List<models.Item> modelItems = new ArrayList<models.Item>(allItems.size());

				for (final Item item : allItems) {
					modelItems.add(item.toModel());
				}
                
				elasticSearch.loadRows(modelItems);
				System.out.println("rows loaded");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(INVENTORY_REFRESH_SLEEP_TIME_MS);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
}

