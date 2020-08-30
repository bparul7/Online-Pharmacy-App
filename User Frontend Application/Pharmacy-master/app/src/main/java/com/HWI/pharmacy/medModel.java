package com.HWI.pharmacy;

public class medModel {
    String image,name;
    public medModel(String image,String name)
    {
        this.image = image;
        this.name = name;
    }
    public medModel(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
