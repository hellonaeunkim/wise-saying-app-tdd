package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingRepository {

  private int lastId;
  private final List<WiseSaying> wiseSayings;

  public WiseSayingRepository() {
    this.lastId = 0;
    this.wiseSayings = new ArrayList<>();
  }


  public WiseSaying save(WiseSaying wiseSaying) {
    wiseSaying.setId(++lastId);
    wiseSayings.add(wiseSaying);
    return wiseSaying;
  }

  public List<WiseSaying> findAll() {
    return wiseSayings;
  }

  public boolean deleteById(int id) {
    return wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == id);
  }

    public Optional<WiseSaying> findById(int id) {
      return wiseSayings.stream()
          .filter(wiseSaying -> wiseSaying.getId() == id)
          .findFirst();
    }
}