package ru.practicum.shareit.user.model;

import ru.practicum.shareit.user.model.dto.UserDto;

public class UserMappper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

    }

    public static User toUser(UserDto userDto) {
        return new User(
                -1,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
