package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    private ObjectId id;
    private String title;
    private String description;
    private Date startDate;
    private Date expiryDate;
    private double price;
    private int amount;
    private boolean enabled;

}
