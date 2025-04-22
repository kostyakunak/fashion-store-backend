package com.kounak.backend.dto;

public class UserWithAddressDTO {
    private RegisterRequest user;
    private String address;

    public RegisterRequest getUser() { return user; }
    public void setUser(RegisterRequest user) { this.user = user; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
} 