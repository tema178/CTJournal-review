package ctjournal.service;

import ctjournal.dto.UserDto;
import ctjournal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repository;

    @Override
    public UserDto find(String name) {
        return UserDto.userToDto(repository.findById(name).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.userListToDtoList(repository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username).orElseThrow(EntityNotFoundException::new);
    }
}
