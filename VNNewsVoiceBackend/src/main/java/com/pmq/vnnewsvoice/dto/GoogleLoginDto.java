package com.pmq.vnnewsvoice.dto;

public class GoogleLoginDto {
    private String tokenId;

    public GoogleLoginDto() {
    }

    public GoogleLoginDto(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
