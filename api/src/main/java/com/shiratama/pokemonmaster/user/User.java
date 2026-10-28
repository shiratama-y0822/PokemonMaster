package com.shiratama.pokemonmaster.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Persistable<String> {

  @Id
  @Column(name = "user_id", length = 16)
  private String userId;

  @Column(name = "nickname", nullable = false, length = 6)
  private String nickname;

  @Column(name = "password", nullable = false, length = 60)
  private String passwordHash;

  @Transient
  private boolean isNew = true;

  public User(String userId, String nickname, String passwordHash) {
    this.userId = userId;
    this.nickname = nickname;
    this.passwordHash = passwordHash;
  }

  @Override
  public String getId() {
    return userId;
  }

  @Override
  public boolean isNew() {
    return isNew;
  }

  @PostLoad
  @PostPersist
  void markNotNew() {
    this.isNew = false;
  }
}