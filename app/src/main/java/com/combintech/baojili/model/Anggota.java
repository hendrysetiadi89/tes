package com.combintech.baojili.model;

/**
 * Created by BayuDarma on 8/4/2017.
 */

public class Anggota {
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
    private String photo;
    public Anggota(String username, String name, String email, String password, String role,String Photo) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.setPhoto(getPhoto());
    }

    public Anggota() {

    }

    public String getUsername (){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}