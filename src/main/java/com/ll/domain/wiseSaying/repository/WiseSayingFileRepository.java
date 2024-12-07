package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository{

  private int lastId;
  private final List<WiseSaying> wiseSayings;

  public WiseSayingFileRepository() {
    this.lastId = 0;
    this.wiseSayings = new ArrayList<>();
  }


  public WiseSaying save(WiseSaying wiseSaying) {
    // 메모리 저장 특성상 새 객체가 아니라면 딱히 할게 없다.
    if (!wiseSaying.isNew()) {
      return wiseSaying;
    }

    // 새로운 객체라면 id를 할당하고 리스트(wiseSayings)에 추가
    wiseSaying.setId(++lastId);

    // toMap() method를 통해 wiseSaying을 Map으로 변환
    // json 형식으로 저장될 Map을 toString method를 통해 JSON으로 변경
    String jsonStr = Util.json.toString(wiseSaying.toMap());
    // json 형식 파일로 저장
    Util.file.set("db/test/wiseSaying/1.json", jsonStr);

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