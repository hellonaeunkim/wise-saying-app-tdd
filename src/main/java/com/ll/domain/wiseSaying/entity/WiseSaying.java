package com.ll.domain.wiseSaying.entity;

import com.ll.standard.util.Util;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.content = (String) map.get("content");
        this.author = (String) map.get("author");
    }

    public WiseSaying(String jsonStr) {
        this(Util.json.toMap(jsonStr));
    }

    public boolean isNew() {
        return id == 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("id", id);
        map.put("content", content);
        map.put("author", author);

        return map;
    }

    // 현재 객체를 -> Map으로 변환 후 -> JSON으로 변환
    public String toJsonStr() {
        return Util.json.toString(toMap());
    }
}
