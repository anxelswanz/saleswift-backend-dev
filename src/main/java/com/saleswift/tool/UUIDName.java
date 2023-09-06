package com.saleswift.tool;

import java.util.UUID;

public class UUIDName {

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replaceAll("-", "");
        return str;
    }
}
