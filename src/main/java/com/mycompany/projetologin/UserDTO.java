package com.mycompany.projetologin;

public class UserDTO {
    int id;
    String username;
    String hashedPassword;

    public UserDTO(int id, String username, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getId() {
        return id;
    }
}
