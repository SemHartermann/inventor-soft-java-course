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
public class Game extends Entity {

    private String name;
    private String genre;

    @Builder
    public Game (Long id, String name, String genre){
        super(id);
        this.name = name;
        this.genre = genre;
    }
}
