package com.example.task.mapper;

import com.example.task.dto.GameRequestDto;
import com.example.task.dto.GameResponseDto;
import com.example.task.entity.Game;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper{
    GameResponseDto gameToGameResponseDto(Game game);
    Game gameRequestDtoToGame(GameRequestDto gameRequestDto);
    List<GameResponseDto> gamesToGameResponseDtos(List<Game> games);
}
