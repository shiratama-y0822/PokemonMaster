package com.shiratama.pokemonmaster.parties;

import java.util.List;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.PartiesListResponse;
import com.shiratama.pokemonmaster.dto.PartiesPostRequest;
import com.shiratama.pokemonmaster.dto.PartiesPutRequest;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartiesController {
  private final PartiesService partyService;

  @GetMapping
  public List<PartiesListResponse> list(@AuthenticationPrincipal Jwt jwt) {
    return partyService.findAllByUserId(jwt.getSubject());
  }

  @PostMapping("/{partyId}/members")
  public ResponseEntity<PartiesListResponse> addMember(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer partyId,
      @Valid @RequestBody PartiesPostRequest request) {

    PartiesListResponse response = partyService.addMemberToParty(
        jwt.getSubject(), partyId, request.boxId());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{partyId}/members/{position}")
  public ResponseEntity<PartiesListResponse> replaceMember(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer partyId,
      @PathVariable int position,
      @Valid @RequestBody PartiesPutRequest request) {

    PartiesListResponse response = partyService.replaceMemberInParty(
        jwt.getSubject(), partyId, position, request.boxId());

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{partyId}/members/{position}")
  public ResponseEntity<Void> removeMember(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer partyId,
      @PathVariable int position) {

    partyService.removeMemberFromParty(
        jwt.getSubject(), partyId, position);

    return ResponseEntity.noContent().build();
  }
}