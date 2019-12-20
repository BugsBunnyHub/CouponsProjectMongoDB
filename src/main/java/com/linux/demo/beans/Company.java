package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

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
        //TODO ask how to insert a list
        //this.coupons = doc.getList("coupons");
    }

    //TODO add coupons
    public Document toDoc() {
        Document doc = new Document();
        doc.put(name, this.name);
        doc.put(email, this.email);
        return doc;
    }


}
