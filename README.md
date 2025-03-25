# Mongo_DB

Autor: **Sebastian Profous**
Datum: **24-03-2025**
# Ausarbeitung der Fragestellungen

#### **1. Vier Vorteile von NoSQL-Datenbanken im Vergleich zu relationalen DBMS**

- **Flexible Datenstruktur:** NoSQL-Datenbanken benötigen kein festes Schema und ermöglichen das Speichern von unstrukturierten oder dynamischen Daten.  
- **Einfache horizontale Skalierung:** Sie lassen sich problemlos über mehrere Server verteilen, was sie ideal für große Datenmengen macht.  
- **Hohe Performance für spezielle Anwendungsfälle:** Durch optimierte Datenzugriffe bieten sie schnelle Lese- und Schreiboperationen.  
- **Unterstützung verschiedener Datenmodelle:** NoSQL-Systeme bieten verschiedene Ansätze wie dokumentenbasierte, key-value-basierte oder graphenbasierte Modelle, die je nach Anwendungsfall vorteilhaft sind.  

---

#### **2. Vier Nachteile von NoSQL-Datenbanken im Vergleich zu relationalen DBMS**  

- **Fehlende Standardisierung:** Es gibt keinen universellen Abfragesprachen-Standard wie SQL, was den Wechsel zwischen verschiedenen NoSQL-Systemen erschwert.  
- **Eingeschränkte ACID-Unterstützung:** Viele NoSQL-Datenbanken verzichten zugunsten der Skalierbarkeit auf vollständige ACID-Transaktionen, was zu Inkonsistenzen führen kann.  
- **Weniger Unterstützung für komplexe Abfragen:** NoSQL-Datenbanken unterstützen keine oder nur eingeschränkte Joins und Aggregationen, wodurch manche Abfragen aufwändiger sind.  
- **Begrenzte Tools und Ökosysteme:** Die Verwaltungs- und Monitoring-Tools für NoSQL-Datenbanken sind oft weniger ausgereift als die für relationale Systeme.  

---

#### **3. Schwierigkeiten bei der Zusammenführung von Daten**  

- **Dateninkonsistenzen:** Unterschiedliche Datenformate und -modelle können zu Problemen führen, wenn Daten aus mehreren Quellen zusammengeführt werden.  
- **Schwieriges Schema-Mapping:** Die Konvertierung von relationalen Tabellen in NoSQL-Strukturen oder umgekehrt kann aufwendig und fehleranfällig sein.  
- **Abweichende Abfragesprachen:** Unterschiedliche NoSQL-Datenbanken nutzen eigene Abfragekonzepte, was die Integration erschwert.  
- **Leistungsprobleme:** Das Zusammenführen großer Datenmengen aus verschiedenen Quellen kann zu Performance-Einbußen führen.  

---

#### **4. Arten von NoSQL-Datenbanken**  

- **Key-Value-Stores:** Speichern Daten als einfache Schlüssel-Wert-Paare.  
- **Dokumentenbasierte Datenbanken:** Nutzen JSON oder BSON zur Speicherung komplexer Dokumente.  
- **Spaltenorientierte Datenbanken:** Organisieren Daten in spaltenbasierten Strukturen für hohe Effizienz bei großen Abfragen.  
- **Graphdatenbanken:** Speichern Daten als Knoten und Beziehungen und sind für vernetzte Daten optimiert.  

---

#### **5. Beispiele für jede NoSQL-Datenbankart**  

- **Key-Value-Store:** Redis  
- **Dokumentenbasierte Datenbank:** MongoDB  
- **Spaltenorientierte Datenbank:** Apache Cassandra  
- **Graphdatenbank:** Neo4j  

---

#### **6. CAP-Theorem und die Begriffe CA, CP, AP**  

Das **CAP-Theorem** besagt, dass ein verteiltes System nur zwei der drei folgenden Eigenschaften gleichzeitig vollständig erfüllen kann:  

- **Konsistenz (Consistency, C):** Alle Knoten im System zeigen jederzeit denselben Datenstand.  
- **Verfügbarkeit (Availability, A):** Jeder Anfrage wird immer eine Antwort geliefert, unabhängig von Ausfällen.  
- **Partitionstoleranz (Partition Tolerance, P):** Das System bleibt funktionsfähig, selbst wenn Netzwerkverbindungen zwischen Knoten unterbrochen sind.  

**Kombinationen gemäß CAP-Theorem:**  
- **CA (Konsistenz + Verfügbarkeit):** Kein Schutz gegen Netzwerkausfälle, das System funktioniert nur, solange alle Knoten erreichbar sind.  
- **CP (Konsistenz + Partitionstoleranz):** Das System bleibt konsistent, kann aber in bestimmten Situationen nicht erreichbar sein.  
- **AP (Verfügbarkeit + Partitionstoleranz):** Das System ist immer verfügbar, aber Daten können vorübergehend inkonsistent sein.  

---

#### **7. MongoDB-Abfragen für Lagerbestand von Produkten**  

**Gesamten Lagerbestand eines bestimmten Produkts über alle Lagerstandorte abrufen:**  
```javascript
db.warehouses.aggregate([
  { $unwind: "$inventory" },
  { $match: { "inventory.productId": "PRODUKT_ID" } },
  { $group: { _id: "$inventory.productId", totalQuantity: { $sum: "$inventory.quantity" } } }
])
```

**Lagerbestand eines bestimmten Produkts an einem bestimmten Lagerstandort abrufen:**  
```javascript
db.warehouses.find(
  { _id: ObjectId("WAREHOUSE_ID"), "inventory.productId": "PRODUKT_ID" },
  { "inventory.$": 1 }
)
```
- `$unwind` wandelt das `inventory`-Array in einzelne Dokumente um.  
- `$match` filtert nach der `productId`.  
- `$group` summiert alle Mengen (`quantity`) für das Produkt.  

# Dokumentation der praktischen Aufgabe

## Aufgabenstellung

Implementieren Sie eine dokumentenorientierte Middleware mit Hilfe von MongoDB, dass Daten über eine REST Schnittstellen empfängt und die Daten des Lagerstandortes in einer MongoDB Datenbank im JSON Format abspeichert. Entwerfen Sie eine geeignet Datenstruktur, um eine kontinuierliche Speicherung der Daten zu gewährleisten.

Es sollen dabei folgende REST-Funktionen implementiert werden:

- POST /warehouse: fügt einen neuen Lagerstandort hinzu.
- GET /warehouse: abrufen aller Lagerstandorte und deren Lagerbestand
- GET /warehouse/{id}: abrufen eines Lagerstandortes id und dessen Lagerbestand
- DELETE /warehouse/{id}: löschen eines Lagerstandortes id
- POST /product: fügt ein neues Produkt und dessen Lagerbestand zu einem Lagerstandort hinzu
- GET /product: abrufen aller Produkte/Lagerbestand und deren Lagerstandort
- GET /product/{id}: abrufen eines Produktes id und dessen Lagerstandorte
- DELETE /product/{id}: löschen eines Produktes id auf einem Lagerstandort

Das Format und in welchen Zeitabständen die Daten eintreffen wird von Ihnen, als System Architekt, spezifiziert und implementiert.

Die Daten werden in der Zentrale in einem MongoDB Repository gespeichert und können hier zu Kontrollzwecken abgerufen werden (mongo Shell).

## Bewertungskriterien

- Gruppengrösse: 1 Person
- Abgabemodus: per Protokoll und Abgabespraech
- Grundlagen Anforderungen **"überwiegend erfüllt"**
    - Installation und Konfiguration einer dokumentenorientierten Middleware mit einem Framework Ihrer Wahl und MongoDB
    - Entwurf und Umsetzung einer entsprechenden JSON Datenstruktur
    - Speicherung der Daten von nur einem Lagerstandort
    - Speicherung der Daten in einer MongoDB Datenbank in der Zentrale
        - mindestens 10 Produkte in 3 Produktkategorien
    - REST API:
        - POST /product, GET /product, GET /warehouse
    - Beantwortung der Fragestellungen
- Grundlagen Anforderungen **"zur Gänze erfüllt"**
    - 5 CRUD Operationen über Mongo Shell Dokumentieren Sie den Mongo Shell Befehl und dessen Ergebnis. Beispiel: ein Produkt hinzufügen, ein Produkt löschen, ein Produkt ändern, ...
- Erweiterte Anforderungen **"überwiegend erfüllt"**
    - Erweiterung der Datenstruktur, sodass ein Speicherung der Daten von mehreren Lagerstandorten möglich ist.
    - REST API: Implementierung der gesamten Schnittstelle, wie in der Angabe beschrieben
    - Implementieren Sie eine kleine Applikation, dass die Daten generiert und über das REST-Interfaces dieser Übung abspeichert. Dabei werden sowohl Produkte, als auch Lagerstandorte abgelegt.
- Erweiterte Anforderungen **"zur Gänze erfüllt"**
    - Formulierung 3 sinnvollen Fragestellung für einen Anwendungsfall in der Zentrale und deren Abfragen in einer Mongo Shell. Beispiel: Wie ist der Lagerbestand von einem Produkt X über alle Lagerstandorte? Welche Produkte haben einen Lagerbestand von unter 10 Stück über alle Lagerstandorte?

## Docker-Container aufsetzen

```zsh
docker pull mongo

docker run -d -p 27017:27017 --name mongo mongo
```

Mit diesen Commands wird das MongoDB Image auf das aktuelle System heruntergeladen und mit den zweiten Command wird ein neuer Container erstellt der dieses Image verwendet.

```zsh
docker exec -it mongo bash  
mongosh
```

Mit diesen Commands kann ich mich in die Console des Containers verbinden und über die Mongoshell Commands ausführen.

## Implementierungen für Gkü

Hinzufügen der Details der Datenbank in der application.properties Datei

```java
  spring.data.mongodb.host=localhost
  spring.data.mongodb.port=27017
  spring.data.mongodb.database=warehouse_db
```

#### Erstellung der Domänenklassen

Hier werden die Datenstrukturen für die Lagerstandorte (Warehouse) und für meine eingebetteten Lagerbestäende (ProductInventory) implementiert. Diese Datenstruktur speichert MongoDB dann als JSON-Dokument ab.

**Warehouse**

```java
@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String id;
    private String name;
    private String location;
    private List<ProductInventory> inventory = new ArrayList<>();
    // Getter und Setter ...
}
```

**ProductInventory**

```java
public class ProductInventory {
    private String productId;
    private String productName;
    private int quantity;
    private Date timestamp; // Zeitpunkt der letzten Änderung
    // Getter und Setter ...
}
```

---

#### Repository-Anbindung

Hier wird noch ein Repository (basierend auf der implementierung MongoRepsoitory) erstellt um CRUD-Operationen zu ermöglichen.

**WarehouseRepository**

```java
public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
```

---

#### Implementierung der REST-Endpunkte

Nun müssen noch die Controller-Klassen implementiert werden mit denen ich meine REST_Schnittstellen bereitstelle. Dabei erstelle ich meine Endpunkte für lagerstandorte und Produkte.

**WarehouseController**

```java
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    
    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    // Weitere Endpunkte: GET/{id}, DELETE/{id}
}
```


**ProductController**

```java
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping
    public ResponseEntity<String> addProductToWarehouse(@RequestBody ProductRequest productRequest) {
        // Suchen des Lagerstandorts und Hinzufügen bzw. Aktualisieren des Produkts
        // ...
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductAggregation> getProductById(@PathVariable String id) {
        // Aggregieren der Produkte über alle Lagerstandorte
        // ...
    }
    // Weitere Endpunkte: GET, DELETE
}
```


---

#### Aggregation und Datenmodellierung

Um die Produkte über alle Lagerstandorte hinweg abzufragen, wird eine Aggregation implementiert.  Hierbei wird für jedes Produkt eine Liste mit den zugehörigen Lagerinformationen (zB Lager-Id, Name, Menge und Zeitstempel) erzeugt.

**ProductController**

```java
@GetMapping
public List<ProductAggregation> getAllProducts() {
    List<Warehouse> warehouses = warehouseRepository.findAll();
    Map<String, ProductAggregation> aggregationMap = new HashMap<>();

    for (Warehouse wh : warehouses) {
        for (ProductInventory pi : wh.getInventory()) {
            // Aggregation der Produktdaten
        }
    }
    return new ArrayList<>(aggregationMap.values());
}
```

### Testen der Applikation Gkü

```zsh
# Neuer Lager
curl -X POST -H "Content-Type: application/json" \
-d '{"name": "Lager Hohenau", "location": "Hohenauerwiese 1, 3400 Kierling"}' \
http://localhost:8080/warehouse

# Alles holen
curl http://localhost:8080/warehouse

# Lager ersetzen
curl http://localhost:8080/warehouse/{id}

# Neues Produkt
curl -X POST -H "Content-Type: application/json" \
-d '{"warehouseId": "67e1c7a6edf952379dd4f0e4", "productId": "13579", "productName": "Conquest of Paradise", "quantity": 50}' \
http://localhost:8080/product

# Produkt weggeben
curl -X DELETE "http://localhost:8080/product/P12345?warehouseId=67e1c7a6edf952379dd4f0e4"

```


### Testen der Applikation Gkv

```java
// Produkt hinzugeben
db.products.insertOne({
    productId: "P1001",
    name: "Gaming Maus",
    category: "Elektronik",
    price: 49.99,
    stock: 150
})

// Produkt abrufen
db.products.findOne({ productId: "P1001" })

// Bestand aktualisieren
db.products.updateOne(
    { productId: "P1001" },
    { $set: { stock: 120 } }
)

// produkt löschen
db.products.deleteOne({ productId: "P1001" })

// alle Produkte anzeigen
db.products.find().pretty()
```
