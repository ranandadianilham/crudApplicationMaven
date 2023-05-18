package com.java.crudApplicationMaven.constant.enumeration;

public enum SortType {
    ASC("ASC", "Sort Ascending"),
    DESC("DESC", "Sort Descending"),
    NULL(null, "Undeclared sorting"),
    ;

    // default sort type constant (desc)
    public static final SortType DEFAULT_SORT_TYPE = DESC;

    String value;
    String message;

    SortType(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String value() {
        return value;
    }

    public String message() {
        return message;
    }

    public boolean isEqual(String value) {
        return value() != null ? value().equalsIgnoreCase(value) : value() == null && value == null ? true : false;
    }

    // get default sort type
    public static SortType of(String value) {
        for (SortType st : SortType.values()) {
            if (st.isEqual(value)) {
                return st;
            }
        }
        return DEFAULT_SORT_TYPE;
    }
}
