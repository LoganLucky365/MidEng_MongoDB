package org.example.mideng_mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "warehouses")
public class Warehouse {

    @Id
    private String id;
    private String name;
    private String location;

    // Eingebettete Liste der Produkte (Inventory)
    private List<ProductInventory> inventory = new ArrayList<>();

    public Warehouse() {}

    public Warehouse(String name, String location, List<ProductInventory> inventory) {
        this.name = name;
        this.location = location;
        this.inventory = inventory;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<ProductInventory> getInventory() { return inventory; }
    public void setInventory(List<ProductInventory> inventory) { this.inventory = inventory; }
}
