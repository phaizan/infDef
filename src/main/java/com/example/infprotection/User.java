package com.example.infprotection;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String login;
    private String password;
    private boolean blocked;
    private boolean passwordRestrctions;
}
