package org.example.mideng_mongodb;

import org.example.mideng_mongodb.ProductAggregation;
import org.example.mideng_mongodb.ProductRequest;
import org.example.mideng_mongodb.WarehouseProductInfo;
import org.example.mideng_mongodb.ProductInventory;
import org.example.mideng_mongodb.Warehouse;
import org.example.mideng_mongodb.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    // POST /product: Fügt ein neues Produkt bzw. erhöht den Lagerbestand in einem Lagerstandort
    @PostMapping
    public ResponseEntity<String> addProductToWarehouse(@RequestBody ProductRequest productRequest) {
        Optional<Warehouse> warehouseOpt = warehouseRepository.findById(productRequest.getWarehouseId());
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            boolean updated = false;
            // Falls das Produkt bereits vorhanden ist, wird der Bestand erhöht
            for (ProductInventory pi : warehouse.getInventory()) {
                if (pi.getProductId().equals(productRequest.getProductId())) {
                    pi.setQuantity(pi.getQuantity() + productRequest.getQuantity());
                    pi.setTimestamp(new Date());
                    updated = true;
                    break;
                }
            }
            // Andernfalls wird ein neuer Eintrag erstellt
            if (!updated) {
                ProductInventory pi = new ProductInventory(
                        productRequest.getProductId(),
                        productRequest.getProductName(),
                        productRequest.getQuantity(),
                        new Date());
                warehouse.getInventory().add(pi);
            }
            warehouseRepository.save(warehouse);
            return ResponseEntity.ok("Produkt hinzugefügt/aktualisiert.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lagerstandort nicht gefunden.");
    }

    // GET /product: Aggregiert alle Produkte über alle Lagerstandorte
    @GetMapping
    public List<ProductAggregation> getAllProducts() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Map<String, ProductAggregation> aggregationMap = new HashMap<>();

        for (Warehouse wh : warehouses) {
            for (ProductInventory pi : wh.getInventory()) {
                ProductAggregation pa = aggregationMap.getOrDefault(pi.getProductId(),
                        new ProductAggregation(pi.getProductId(), pi.getProductName(), new ArrayList<>()));
                pa.getWarehouses().add(new WarehouseProductInfo(
                        wh.getId(), wh.getName(), pi.getQuantity(), pi.getTimestamp()));
                aggregationMap.put(pi.getProductId(), pa);
            }
        }
        return new ArrayList<>(aggregationMap.values());
    }

    // GET /product/{id}: Ruft ein bestimmtes Produkt und dessen Lagerstandorte ab
    @GetMapping("/{id}")
    public ResponseEntity<ProductAggregation> getProductById(@PathVariable String id) {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        ProductAggregation result = null;
        for (Warehouse wh : warehouses) {
            for (ProductInventory pi : wh.getInventory()) {
                if (pi.getProductId().equals(id)) {
                    if (result == null) {
                        result = new ProductAggregation(pi.getProductId(), pi.getProductName(), new ArrayList<>());
                    }
                    result.getWarehouses().add(new WarehouseProductInfo(
                            wh.getId(), wh.getName(), pi.getQuantity(), pi.getTimestamp()));
                }
            }
        }
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // DELETE /product/{id}?warehouseId=...: Löscht ein Produkt aus einem spezifischen Lagerstandort
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductFromWarehouse(@PathVariable String id,
                                                             @RequestParam String warehouseId) {
        Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            boolean removed = warehouse.getInventory().removeIf(pi -> pi.getProductId().equals(id));
            if (removed) {
                warehouseRepository.save(warehouse);
                return ResponseEntity.ok("Produkt gelöscht.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produkt nicht im Lager gefunden.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lagerstandort nicht gefunden.");
    }
}
