package com.shiratama.pokemonmaster.boxes;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.shiratama.pokemonmaster.dto.BoxesDetailResponse;
import com.shiratama.pokemonmaster.dto.BoxesListResponse;
import com.shiratama.pokemonmaster.dto.BoxesPatchRequest;
import com.shiratama.pokemonmaster.dto.BoxesPatchResponse;
import com.shiratama.pokemonmaster.dto.BoxesPostRequest;
import com.shiratama.pokemonmaster.dto.BoxesPostResponse;
import com.shiratama.pokemonmaster.repository.Box;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class BoxesController {
  private final BoxesService boxService;

  @GetMapping
  public List<BoxesListResponse> list(
      @AuthenticationPrincipal Jwt jwt,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer pokemonId,
      @RequestParam(required = false) Integer typeId) {

    return boxService.search(jwt.getSubject(), name, pokemonId, typeId);
  }

  @GetMapping("/{boxId}")
  public ResponseEntity<BoxesDetailResponse> detail(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer boxId) {
    return boxService.findDetail(jwt.getSubject(), boxId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @PostMapping
  public ResponseEntity<BoxesPostResponse> create(
      @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody BoxesPostRequest request) {
    Box box = boxService.create(jwt.getSubject(), request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BoxesPostResponse.from(box));
  }

  @PatchMapping("/{boxId}")
  public ResponseEntity<BoxesPatchResponse> update(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer boxId,
      @Valid @RequestBody BoxesPatchRequest request) {
    Box box = boxService.update(jwt.getSubject(), boxId, request);
    return ResponseEntity.ok(BoxesPatchResponse.from(box));
  }

  @DeleteMapping("/{boxId}")
  public ResponseEntity<Void> delete(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer boxId) {
    boxService.delete(jwt.getSubject(), boxId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ProblemDetail handleInvalidBoxId(MethodArgumentTypeMismatchException e) {
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST, "入力内容を確認してください");
  }
}