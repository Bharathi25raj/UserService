package com.bharathi.userservice.models;

import jakarta.persistence.ManyToOne;

import java.util.Date;

public class Token extends BaseModel {
    private String value;
    private Date expiryAt;

    @ManyToOne
    private User user;


    /*
    *   1                  1
    *
    * Token ------------- User
    *
    *   M                  1
    *
    *  ==> M : 1
    * */
}
