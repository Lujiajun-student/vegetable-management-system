package com.vegetable.entity;

public enum ProductCategory {
    LEAFY_GREEN("叶菜类"),
    ROOT_VEGETABLE("根茎类"),
    FRUIT_VEGETABLE("果菜类"),
    MUSHROOM("菌菇类"),
    BEAN("豆类"),
    OTHER("其他");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 