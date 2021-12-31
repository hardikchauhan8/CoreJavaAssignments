package com.zensar.assignment4.models;

import java.util.Arrays;

public enum Status {
    NEW(1),
    REJECTED(2),
    ACCEPTED(3),
    COMPLETED(4);

    private final int status;

    Status(int i) {
        status = i;
    }

    public static Status getStatus(int status) {
        return Arrays.stream(values()).filter(i -> i.status == status).limit(1).findFirst().get();
    }
}