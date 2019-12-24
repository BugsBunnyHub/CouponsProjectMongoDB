package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private ObjectId id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    private List<ObjectId> coupons;

    public Customer(Document doc) {
        this.id = doc.getObjectId("_id");
        this.firstName = doc.getString("firstName");
        this.lastName = doc.getString("lastName");
        this.email = doc.getString("email");
        //from stream of Coupons to stream of ObjId then collect to covert it to List
        this.coupons = doc.getList("coupons", Coupon.class)
                .stream()
                // :: for each obj(Coupon) use method (getId)
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    //TODO add coupons
    public Document toDoc() {
        Document doc = new Document();
        doc.put("firstName", this.firstName);
        doc.put("lastName", this.lastName);
        doc.put("email", this.email);
        doc.put("coupons", this.coupons);
        return doc;
    }




}
