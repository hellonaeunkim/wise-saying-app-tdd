package com.ll.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilTest {
    @Test
    @DisplayName("Map to JSON (필드 1개)")
    public void t1() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "이름");

        String jsonStr = Util.json.toString(map);

        assertThat(jsonStr).isEqualTo("""
                {
                    "name": "이름"
                }
                """.stripIndent().trim());

    }

    @Test
    @DisplayName("Map to JSON (필드 2개)")
    public void t2() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "이름");
        map.put("gender", "남자");

        String jsonStr = Util.json.toString(map);

        assertThat(jsonStr).isEqualTo("""
                {
                    "name": "이름",
                    "gender": "남자"
                }
                """.stripIndent().trim());

    }

    @Test
    @DisplayName("Map to JSON (정수 필드)")
    public void t3() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("name", "이름");
        map.put("gender", "남자");

        String jsonStr = Util.json.toString(map);

        assertThat(jsonStr).isEqualTo("""
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자"
                }
                """.stripIndent().trim());

    }

    @Test
    @DisplayName("Map to JSON (실수 필드)")
    public void t4() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("name", "이름");
        map.put("gender", "남자");
        map.put("height", "180.12345");

        String jsonStr = Util.json.toString(map);

        assertThat(jsonStr).isEqualTo("""
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자",
                    "height": "180.12345"
                }
                """.stripIndent().trim());

    }

    @Test
    @DisplayName("Map to JSON (논리 필드)")
    public void t5() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("name", "이름");
        map.put("gender", "남자");
        map.put("height", 178.1543221);
        map.put("married", true);

        String jsonStr = Util.json.toString(map);

        assertThat(jsonStr).isEqualTo("""
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자",
                    "height": 178.1543221,
                    "married": true
                }
                """.stripIndent().trim());

    }

    @Test
    @DisplayName("JSON to Map (필드 1개)")
    public void t6() {

        String jsonStr = """
                {
                    "name": "이름",
                }
                """.stripIndent().trim();


        Map<String, Object> map = Util.json.toMap(jsonStr);


        assertThat(map).containsEntry("name", "이름");
    }

    @Test
    @DisplayName("JSON to Map (필드 2개)")
    public void t7() {

        String jsonStr = """
                {
                    "name": "이름",
                    "gender": "남자"
                }
                """.stripIndent().trim();


        Map<String, Object> map = Util.json.toMap(jsonStr);


        assertThat(map)
                .containsEntry("name", "이름")
                .containsEntry("gender", "남자");
    }

    @Test
    @DisplayName("JSON to Map (정수 필드)")
    public void t8() {

        String jsonStr = """
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자"
                }
                """.stripIndent().trim();

        Map<String, Object> map = Util.json.toMap(jsonStr);

        assertThat(map)
                .containsEntry("id", 1)
                .containsEntry("name", "이름")
                .containsEntry("gender", "남자");
    }

    @Test
    @DisplayName("JSON to Map (실수 필드)")
    public void t9() {

        String jsonStr = """
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자",
                    "height": 178.1543221
                }
                """.stripIndent().trim();

        Map<String, Object> map = Util.json.toMap(jsonStr);

        assertThat(map)
                .containsEntry("id", 1)
                .containsEntry("name", "이름")
                .containsEntry("gender", "남자")
                .containsEntry("height", 178.1543221);
    }

    @Test
    @DisplayName("JSON to Map (논리 필드)")
    public void t10() {

        String jsonStr = """
                {
                    "id": 1,
                    "name": "이름",
                    "gender": "남자",
                    "height": 178.1543221,
                    "married": false
                }
                """.stripIndent().trim();

        Map<String, Object> map = Util.json.toMap(jsonStr);

        assertThat(map)
                .containsEntry("id", 1)
                .containsEntry("name", "이름")
                .containsEntry("gender", "남자")
                .containsEntry("height", 178.1543221)
                .containsEntry("married", false);
    }
}
