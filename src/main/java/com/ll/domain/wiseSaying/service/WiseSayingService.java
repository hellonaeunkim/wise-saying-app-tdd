package com.ll.domain.wiseSaying.service;

import com.ll.domain.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {

  private int lastId;
  private final List<WiseSaying> wiseSayings;

  public WiseSayingService() {
    this.lastId = 0;
    this.wiseSayings = new ArrayList<>();
  }


  public WiseSaying add(String content, String author) {
    WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
    wiseSayings.add(wiseSaying);
    return wiseSaying;
  }

  public List<WiseSaying> findAll() {
    return wiseSayings;
  }
}
