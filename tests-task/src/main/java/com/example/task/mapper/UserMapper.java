package com.example.task.mapper;

import com.example.task.dto.UserRequestDto;
import com.example.task.dto.UserResponseDto;
import com.example.task.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto userToUserResponseDto(User user);
    UserRequestDto userToUserRequestDto(User user);
    User userRequestDtoToUser(UserRequestDto userRequestDto);
    List<UserResponseDto> usersToUserResponseDtos(List<User> users);
}
