package org.example;

import lombok.*;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
}
