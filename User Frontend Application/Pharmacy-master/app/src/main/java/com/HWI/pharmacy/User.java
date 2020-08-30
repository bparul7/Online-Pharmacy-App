package com.HWI.pharmacy;

import java.io.File;

public class User {
    private String name,address,email;
    private File userImage,idImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public File getUserImage() {
        return userImage;
    }

    public void setUserImage(File userImage) {
        this.userImage = userImage;
    }

    public File getIdImage() {
        return idImage;
    }

    public void setIdImage(File idImage) {
        this.idImage = idImage;
    }
}
