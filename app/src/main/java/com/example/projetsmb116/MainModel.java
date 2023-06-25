package com.example.projetsmb116;

public class MainModel {
    //Company
    String Address;
    String City;
    String Company;
    String FirstName;
    String Phone;
    String UserName;

    //Product
    String ProductCategory;
    String ProductName;
    String ProductQuantity;


    //Constructeur sans argument
    MainModel()
    {

    }

    //Constructeur avec argument
    public MainModel(String address, String city, String company, String firstName, String phone, String userName, String productCategory, String productName, String productQuantity) {
        Address = address;
        City = city;
        Company = company;
        FirstName = firstName;
        Phone = phone;
        UserName = userName;
        ProductCategory = productCategory;
        ProductName = productName;
        ProductQuantity = productQuantity;
    }



    //GETTER/SETTER
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }



}
