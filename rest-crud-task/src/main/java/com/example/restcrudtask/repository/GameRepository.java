package com.example.restcrudtask.repository;

import com.example.restcrudtask.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    /*List<Game> findAll();
    Game findById();*/

}