package com.mt.mtSocialMedia.enums;


public enum Reaction {
    LIKE(0), LOVE(1), HAHA(2), SAD(3), ANGRY(4);
    private final int value;
    Reaction(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}