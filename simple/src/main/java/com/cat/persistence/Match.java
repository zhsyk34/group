package com.cat.persistence;

public interface Match {
    boolean test(String reference, String target);
}