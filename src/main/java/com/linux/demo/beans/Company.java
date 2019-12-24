package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Data generate getters/setter/tostring etc
@Data
// con with full parameters
@AllArgsConstructor
// con without any parameters
@NoArgsConstructor
public class Company {

    //ObjectId is mongo PK
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private List<ObjectId> coupons;

    public Company(Document doc) {
        this.id = doc.getObjectId("_id");
        this.name = doc.getString("name");
        this.email = doc.getString("email");
        //from stream of Coupons to stream of ObjId then collect to covert it to List
        this.coupons = doc.getList("coupons", Coupon.class)
                .stream()
                // :: for each obj(Coupon) use method (getId)
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    public Document toDoc() {
        Document doc = new Document();
        doc.put("name", this.name);
        doc.put("email", this.email);
        doc.put("coupons", this.coupons);
        return doc;
    }


}
