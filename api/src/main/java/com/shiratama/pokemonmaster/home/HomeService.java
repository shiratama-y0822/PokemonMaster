package com.shiratama.pokemonmaster.home;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.shiratama.pokemonmaster.dto.HomeResponse;
import com.shiratama.pokemonmaster.repository.BoxesRepository;
import com.shiratama.pokemonmaster.repository.User;
import com.shiratama.pokemonmaster.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final UsersRepository userRepository;
  private final BoxesRepository boxesRepository;

  @Transactional(readOnly = true)
  public HomeResponse getHome(String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.UNAUTHORIZED, "ユーザーが見つかりません"));

    long pokemonCaught = boxesRepository.countByUserId(userId);

    return new HomeResponse(user.getNickname(), pokemonCaught);
  }
}