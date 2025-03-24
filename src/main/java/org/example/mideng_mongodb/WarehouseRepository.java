package org.example.mideng_mongodb;

import org.example.mideng_mongodb.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
