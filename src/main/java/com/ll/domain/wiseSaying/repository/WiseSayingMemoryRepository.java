package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingMemoryRepository implements WiseSayingRepository {

    private int lastId;
    private final List<WiseSaying> wiseSayings;

    public WiseSayingMemoryRepository() {
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