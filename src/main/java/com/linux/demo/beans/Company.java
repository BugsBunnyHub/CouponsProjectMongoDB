package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String name;
    private String email;
    private List<ObjectId> coupons;

}
