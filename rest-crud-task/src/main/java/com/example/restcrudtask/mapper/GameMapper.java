package com.example.restcrudtask.mapper;

import com.example.restcrudtask.dto.GameRequestDto;
import com.example.restcrudtask.dto.GameResponseDto;
import com.example.restcrudtask.entity.Game;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper{
    GameResponseDto gameToGameResponseDto(Game game);
    Game gameRequestDtoToGame(GameRequestDto gameRequestDto);
    List<GameResponseDto> gamesToGameResponseDtos(List<Game> games);
}
