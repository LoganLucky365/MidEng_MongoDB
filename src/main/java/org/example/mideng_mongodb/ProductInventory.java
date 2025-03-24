package org.example.mideng_mongodb;

import java.util.Date;

public class ProductInventory {

    private String productId;
    private String productName;
    private int quantity;
    private Date timestamp; // Zeitpunkt der letzten Änderung

    public ProductInventory() {}

    public ProductInventory(String productId, String productName, int quantity, Date timestamp) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    // Getter & Setter
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
