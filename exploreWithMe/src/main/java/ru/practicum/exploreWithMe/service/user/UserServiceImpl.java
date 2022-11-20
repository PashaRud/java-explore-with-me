package ru.practicum.exploreWithMe.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.user.UserDto;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.mapper.user.UserMapper;
import ru.practicum.exploreWithMe.model.User;
import ru.practicum.exploreWithMe.repository.user.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {

        Pageable pageable = FromSizeRequest.of(from, size);

        List<User> users = userRepository.findByIdIn(ids, pageable);

        return UserMapper.toUserDtoList(users);
    }

//    @Override
//    public UserDto getUserById(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if(!user.isPresent()) {
//            return null;
//        }
//        return UserMapper.toUserDto(user.get());
//    }

    @Transactional
    public UserDto create(UserDto dto) {
        User user = UserMapper.toUser(dto);
        user = userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

//    @Override
//    @Transactional
//    public UserDto update(UserDto userDto, Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("User with id = " + id + " was not found."));
//        if (userDto.getName() != null) {
//            user.setName(userDto.getName());
//        }
//        if ((userDto.getEmail() != null) && (userDto.getEmail() != user.getEmail())) {
//            if (userRepository.findByEmail(userDto.getEmail())
//                    .stream()
//                    .filter(u -> u.getEmail().equals(userDto.getEmail()))
//                    .allMatch(u -> u.getId().equals(userDto.getId()))) {
//                user.setEmail(userDto.getEmail());
//            } else {
//                throw new AlreadyExistsException("User with email = " + userDto.getEmail() + " already exist!");
//            }
//
//        }
//
//        return UserMapper.toUserDto(userRepository.save(user));
//    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
