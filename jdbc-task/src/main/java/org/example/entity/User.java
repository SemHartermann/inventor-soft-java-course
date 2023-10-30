package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends Entity{
    private String email;
    private String password;
    private Role role;

    @Builder
    public User (Long id, String email, String password){
        super(id);
        this.email = email;
        this.password = password;
    }
}
