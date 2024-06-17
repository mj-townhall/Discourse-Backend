package com.townhall.discourse.dto;

public class CredentialsDto {
    private final String email;
    private final char[] password;

    public CredentialsDto(String email, char[] password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    // Optionally, you may override equals(), hashCode(), and toString() methods
    // based on the fields of the class
}

