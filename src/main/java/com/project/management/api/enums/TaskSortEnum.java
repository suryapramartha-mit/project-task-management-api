package com.project.management.api.enums;

public enum TaskSortEnum {
    PRIORITY("priority"),
    DUE_DATE("dueDate");

    private final String field;

    TaskSortEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

