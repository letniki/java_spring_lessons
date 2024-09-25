package org.okten.springdemo.config.properties;

import lombok.Data;

@Data
public class Office {

    private String name;

    private String contactNumber;

    private Address address;
}
