package ru.practicum.exploreWithMe.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;
import ru.practicum.exploreWithMe.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getEmail(),
                user.getName());
    }

    public static User NewUserToUser(NewUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getEmail(),
                userDto.getName());
    }

    public static User toUserFromShortDto(UserShortDto userShortDto) {
        return User.builder()
                .id(userShortDto.getId())
                .name(userShortDto.getName())
                .build();
    }

    public static UserShortDto toShortUserDto(User user) {
        return new UserShortDto(user.getId(),
                user.getName());
    }

    public static List<UserDto> toUserDtoList(Iterable<User> users) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(toUserDto(user));
        }
        return result;
    }
}
