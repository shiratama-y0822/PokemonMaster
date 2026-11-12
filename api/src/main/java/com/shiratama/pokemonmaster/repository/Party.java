package com.shiratama.pokemonmaster.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parties")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "party_id")
  private Integer partyId;

  @Column(name = "party_name", nullable = false, length = 16)
  private String partyName;

  @Column(name = "user_id", nullable = false, length = 16)
  private String userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position1")
  private Box position1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position2")
  private Box position2;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position3")
  private Box position3;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position4")
  private Box position4;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position5")
  private Box position5;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position6")
  private Box position6;

  public boolean containsBox(Integer boxId) {
    return (position1 != null && position1.getBoxId().equals(boxId))
        || (position2 != null && position2.getBoxId().equals(boxId))
        || (position3 != null && position3.getBoxId().equals(boxId))
        || (position4 != null && position4.getBoxId().equals(boxId))
        || (position5 != null && position5.getBoxId().equals(boxId))
        || (position6 != null && position6.getBoxId().equals(boxId));
  }

  public int addMemberToFirstEmptyPosition(Box box) {
    if (position1 == null) {
      position1 = box;
      return 1;
    }
    if (position2 == null) {
      position2 = box;
      return 2;
    }
    if (position3 == null) {
      position3 = box;
      return 3;
    }
    if (position4 == null) {
      position4 = box;
      return 4;
    }
    if (position5 == null) {
      position5 = box;
      return 5;
    }
    if (position6 == null) {
      position6 = box;
      return 6;
    }
    return 0;
  }

  public Box getMemberAtPosition(int position) {
    return switch (position) {
      case 1 -> position1;
      case 2 -> position2;
      case 3 -> position3;
      case 4 -> position4;
      case 5 -> position5;
      case 6 -> position6;
      default -> throw new IllegalArgumentException("position must be 1-6");
    };
  }

  public void replaceMemberAtPosition(int position, Box box) {
    switch (position) {
      case 1 -> position1 = box;
      case 2 -> position2 = box;
      case 3 -> position3 = box;
      case 4 -> position4 = box;
      case 5 -> position5 = box;
      case 6 -> position6 = box;
      default -> throw new IllegalArgumentException("position must be 1-6");
    }
  }

  public void removeMemberAtPosition(int position) {
    switch (position) {
      case 1 -> position1 = null;
      case 2 -> position2 = null;
      case 3 -> position3 = null;
      case 4 -> position4 = null;
      case 5 -> position5 = null;
      case 6 -> position6 = null;
      default -> throw new IllegalArgumentException("position must be 1-6");
    }
  }
}