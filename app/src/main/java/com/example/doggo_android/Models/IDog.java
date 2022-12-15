package com.example.doggo_android.Models;

public class IDog {
    private String userID;
    private String name;
    private String breed;
    private String age;

    public IDog(String userID, String name, String breed, String age) {
        this.userID = userID;
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getAge() {
        return age;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "IDog{" +
                "userID='" + userID + '\'' + name + '\'' + breed + '\'' + age + '\'' ;
    }

}
