package com.shiratama.pokemonmaster.parties;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.shiratama.pokemonmaster.dto.PartiesListResponse;
import com.shiratama.pokemonmaster.repository.Box;
import com.shiratama.pokemonmaster.repository.BoxesRepository;
import com.shiratama.pokemonmaster.repository.PartiesRepository;
import com.shiratama.pokemonmaster.repository.Party;
import com.shiratama.pokemonmaster.repository.Pokemon;

@Service
@RequiredArgsConstructor
public class PartiesService {
  private final PartiesRepository partiesRepository;
  private final BoxesRepository boxesRepository;

  @Transactional(readOnly = true)
  public List<PartiesListResponse> findAllByUserId(String userId) {
    return partiesRepository.findByUserIdOrderByPartyIdAsc(userId)
        .stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public PartiesListResponse addMemberToParty(
      String userId, Integer partyId, Integer boxId) {

    Party party = partiesRepository.findById(partyId)
        .filter(found -> found.getUserId().equals(userId))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたパーティが見つかりません。"));

    Box box = boxesRepository.findByBoxIdAndUserId(boxId, userId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたボックスが見つかりません。"));

    if (party.containsBox(boxId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "このボックスは既にパーティに登録されています。");
    }

    int position = party.addMemberToFirstEmptyPosition(box);
    if (position == 0) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "パーティに空き枠がありません。");
    }

    return toResponse(party);
  }

  private PartiesListResponse toResponse(Party party) {
    List<PartiesListResponse.Member> members = new ArrayList<>();

    addMember(members, 1, party.getPosition1());
    addMember(members, 2, party.getPosition2());
    addMember(members, 3, party.getPosition3());
    addMember(members, 4, party.getPosition4());
    addMember(members, 5, party.getPosition5());
    addMember(members, 6, party.getPosition6());

    return new PartiesListResponse(
        party.getPartyId(),
        party.getPartyName(),
        members);
  }

  private void addMember(
      List<PartiesListResponse.Member> members,
      int position,
      Box box) {
    if (box == null) {
      return;
    }

    Pokemon pokemon = box.getPokemon();
    members.add(new PartiesListResponse.Member(
        box.getBoxId(),
        position,
        pokemon.getName(),
        "/image/" + pokemon.getPokemonId() + ".png"));
  }

  @Transactional
  public PartiesListResponse replaceMemberInParty(
      String userId, Integer partyId, int position, Integer boxId) {

    if (position < 1 || position > 6) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "位置は1〜6で指定してください。");
    }

    Party party = partiesRepository.findById(partyId)
        .filter(found -> found.getUserId().equals(userId))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたパーティが見つかりません。"));

    Box current = party.getMemberAtPosition(position);
    if (current == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "指定された位置にメンバーがいません。");
    }

    Box replacement = boxesRepository.findByBoxIdAndUserId(boxId, userId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたボックスが見つかりません。"));

    if (current.getBoxId().equals(boxId)) {
      return toResponse(party);
    }

    if (party.containsBox(boxId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "このボックスは別の位置に登録されています。");
    }

    party.replaceMemberAtPosition(position, replacement);
    return toResponse(party);
  }

  @Transactional
  public void removeMemberFromParty(
      String userId, Integer partyId, int position) {

    if (position < 1 || position > 6) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "位置は1〜6で指定してください。");
    }

    Party party = partiesRepository.findById(partyId)
        .filter(found -> found.getUserId().equals(userId))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたパーティが見つかりません。"));

    if (party.getMemberAtPosition(position) == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "指定された位置にメンバーがいません。");
    }

    party.removeMemberAtPosition(position);
  }
}