package com.kounak.backend.dto;

public class UserWithAddressDTO {
    private UserRequestDTO user;
    private AddressRequestDTO address;

    public UserRequestDTO getUser() { return user; }
    public void setUser(UserRequestDTO user) { this.user = user; }

    public AddressRequestDTO getAddress() { return address; }
    public void setAddress(AddressRequestDTO address) { this.address = address; }
} 