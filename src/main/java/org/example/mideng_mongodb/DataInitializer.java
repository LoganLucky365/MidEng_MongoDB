package org.example.mideng_mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public void run(String... args) throws Exception {
        Warehouse warehouse = new Warehouse("W1001", "Berlin", new ArrayList<>());
        warehouse.getInventory().add(new ProductInventory("P1001", "Gaming Maus", 50, new Date()));
        warehouse.getInventory().add(new ProductInventory("P1002", "Tastatur", 30, new Date()));
        warehouseRepository.save(warehouse);
    }
}
