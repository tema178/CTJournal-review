package ctjournal.service;

import ctjournal.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto find(String name);

    List<UserDto> findAll();
}
