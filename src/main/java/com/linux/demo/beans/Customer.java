package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private ObjectId id;
    private String firstname;
    private String lastname;
    private String email;
    private List<ObjectId> coupons;

}
