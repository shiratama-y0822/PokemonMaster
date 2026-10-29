package com.shiratama.pokemonmaster.login;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.dto.LoginPostRequest;
import com.shiratama.pokemonmaster.dto.LoginPostResponse;
import com.shiratama.pokemonmaster.exception.LoginFailedException;
import com.shiratama.pokemonmaster.repository.User;
import com.shiratama.pokemonmaster.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final UsersRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public LoginPostResponse login(LoginPostRequest request) {
    User user = userRepository.findById(request.userId())
        .orElseThrow(LoginFailedException::new);

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new LoginFailedException();
    }

    return LoginPostResponse.from(user);

    // userIdとpasswordが一致するか確認してほしい
    // 一致していたらResponseにnicknameとsetCookieを出力してほしい
    // 不一致の場合は、入力エラー文を表示してほしい
    //
  }
}