package com.example.hallbooking;

public class UserModel {
    String FullName,FatherName,Email,Passwrod,CNIC,Address,Phone,Image;

    public UserModel() {
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPasswrod() {
        return Passwrod;
    }

    public void setPasswrod(String passwrod) {
        Passwrod = passwrod;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getAddress() {
        return Address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public UserModel(String fullName, String fatherName, String email, String passwrod, String CNIC, String address, String phone, String image) {
        FullName = fullName;
        FatherName = fatherName;
        Email = email;
        Passwrod = passwrod;
        this.CNIC = CNIC;
        Address = address;
        Phone = phone;
        Image = image;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


}
