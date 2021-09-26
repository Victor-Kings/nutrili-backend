package com.nutrili.Utils;

import com.nutrili.external.database.entity.Address;

public class GenericMethods {
    public static <T> T nvl(T a, T b) {
        return (a == null)?b:a;
    }

    public static String mountAddress(Address address){
        return address.getStreet()+", "+address.getNumber()+", "+address.getNeighborhood()+", "+address.getCity()+"-"+address.getState();
    }
}
