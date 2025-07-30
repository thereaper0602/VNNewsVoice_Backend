package com.pmq.vnnewsvoice.enums;

public enum ArticleStatus {
    DRAFT("Draft"),
    PUBLISHED("Published"),
    PENDING("Pending"),
    REJECTED("Rejected");

    private final String status;

    ArticleStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
