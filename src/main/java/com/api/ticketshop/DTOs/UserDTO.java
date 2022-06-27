package com.api.ticketshop.DTOs;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    private Integer id;
    @Valid
    private BillingAddressDTO address;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String surname;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String password;
    @NotNull @NotEmpty
    private String cpf;
    @NotNull @NotEmpty
    private String tel;
    @NotNull
    private Integer Type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BillingAddressDTO getAddress() {
        return address;
    }

    public void setAddress(BillingAddressDTO address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }
}
