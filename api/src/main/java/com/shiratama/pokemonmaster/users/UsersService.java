package com.shiratama.pokemonmaster.users;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.dto.UsersPostRequest;
import com.shiratama.pokemonmaster.exception.DuplicateUserIdException;
import com.shiratama.pokemonmaster.repository.User;
import com.shiratama.pokemonmaster.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersService {

  private final UsersRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User create(UsersPostRequest request) {
    if (userRepository.existsById(request.userId())) {
      throw new DuplicateUserIdException(request.userId());
    }
    User user = new User(request.userId(), request.nickname(), passwordEncoder.encode(request.password()));
    try {
      return userRepository.saveAndFlush(user);
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateUserIdException(request.userId());
    }
  }
}