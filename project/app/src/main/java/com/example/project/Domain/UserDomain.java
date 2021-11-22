package com.example.project.Domain;

public class UserDomain {
    String id;
    String email, name, password, tel;
    String birthday = "", gender = "", avatar = "";

    public UserDomain(String id,String email, String name, String password, String tel, String birthday, String gender, String avatar) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
        this.birthday = birthday;
        this.gender = gender;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getFullName() {
        return name;
    }

    public void setFullName(String name) { this.name = name; }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) { this.tel = tel; }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) { this.gender = gender; }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
