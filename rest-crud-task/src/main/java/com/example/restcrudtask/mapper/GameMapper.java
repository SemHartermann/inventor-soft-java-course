package com.example.restcrudtask.mapper;

import com.example.restcrudtask.dto.RequestGameDto;
import com.example.restcrudtask.dto.RespondGameDto;
import com.example.restcrudtask.entity.Game;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper{

    RespondGameDto gameToRespondGameDto(Game game);
    Game requestGameDtoToGame(RequestGameDto requestGameDto);
    List<RespondGameDto> gamesToRespondGameDtos(List<Game> games);

}
