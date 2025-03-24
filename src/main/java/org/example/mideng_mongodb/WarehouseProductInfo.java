package org.example.mideng_mongodb;

import java.util.Date;

public class WarehouseProductInfo {
    private String warehouseId;
    private String warehouseName;
    private int quantity;
    private Date timestamp;

    public WarehouseProductInfo() {}

    public WarehouseProductInfo(String warehouseId, String warehouseName, int quantity, Date timestamp) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    // Getter & Setter
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
