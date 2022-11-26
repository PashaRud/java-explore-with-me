package ru.practicum.exploreWithMe.user.service;

import ru.practicum.exploreWithMe.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

//    UserDto getUserById(Long id);

    UserDto create(NewUserRequest newUserRequest);

//    UserDto update(UserDto userDto, Long id);

    void delete(Long userId);

}
