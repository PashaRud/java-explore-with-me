package ru.practicum.exploreWithMe.service.user;

import ru.practicum.exploreWithMe.dto.user.UserDto;
import ru.practicum.exploreWithMe.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

//    UserDto getUserById(Long id);

    UserDto create(UserDto userDto);

//    UserDto update(UserDto userDto, Long id);

    void delete(Long userId);

}
