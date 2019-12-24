package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Date startDate;
    @NonNull
    private Date expiryDate;
    @NonNull
    private double price;
    @NonNull
    private int amount;
    @NonNull
    private boolean enabled;

    public Coupon (Document doc) {
        this.id = doc.getObjectId("_id");
        this.title = doc.getString("title");
        this.description = doc.getString("description");
        this.startDate = doc.getDate("startDate");
        this.expiryDate = doc.getDate("expiryDate");
        this.price = doc.getDouble("price");
        this.amount = doc.getInteger("amount");
        this.enabled = doc.getBoolean("enabled");
    }

    public Document toDoc() {
        Document doc = new Document();
        doc.put("title", this.title);
        doc.put("description", this.description);
        doc.put("startDate", this.startDate);
        doc.put("expiryDate", this.expiryDate);
        doc.put("price", this.price);
        doc.put("amount", this.amount);
        doc.put("enabled", this.enabled);
        return doc;
    }

}
