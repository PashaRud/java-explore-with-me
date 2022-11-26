package ru.practicum.exploreWithMe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.user.dto.NewUserRequest;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.user.mapper.UserMapper;
import ru.practicum.exploreWithMe.user.model.User;
import ru.practicum.exploreWithMe.user.repository.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;


import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {

        Pageable pageable = FromSizeRequest.of(from, size);

        List<User> users = userRepository.findByIdIn(ids, pageable);

        return UserMapper.toUserDtoList(users);
    }

    @Transactional
    public UserDto create(NewUserRequest newUser) {
        try {
            log.info("Create name: " + newUser.toString());
            return  UserMapper.toUserDto(userRepository.save(UserMapper.NewUserToUser(newUser)));
        } catch (RuntimeException e) {
            throw new AlreadyExistsException("Name must be unique.");
        }
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
