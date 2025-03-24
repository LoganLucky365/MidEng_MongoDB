package org.example.mideng_mongodb;

import java.util.List;

public class ProductAggregation {

    private String productId;
    private String productName;
    private List<WarehouseProductInfo> warehouses;

    public ProductAggregation() {}

    public ProductAggregation(String productId, String productName, List<WarehouseProductInfo> warehouses) {
        this.productId = productId;
        this.productName = productName;
        this.warehouses = warehouses;
    }

    // Getter & Setter
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public List<WarehouseProductInfo> getWarehouses() { return warehouses; }
    public void setWarehouses(List<WarehouseProductInfo> warehouses) { this.warehouses = warehouses; }
}
