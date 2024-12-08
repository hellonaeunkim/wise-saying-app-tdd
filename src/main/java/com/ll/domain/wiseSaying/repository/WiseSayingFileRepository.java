package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository{

  // .json 파일 경로를 받아오는 method
  public static String getTableDirPath() {
    return "db/test/wiseSaying";
  }

  public static String getRowFilePath(int id) {
    return getTableDirPath() + "/" + id + ".json";
  }

  public WiseSaying save(WiseSaying wiseSaying) {
    // 메모리 저장 특성상 새 객체가 아니라면 딱히 할게 없다.
    if (!wiseSaying.isNew()) {
      return wiseSaying;
    }

    wiseSaying.setId(
            findAll().size() + 1
    );

    // toMap() method를 통해 wiseSaying을 Map으로 변환
    // json 형식으로 저장될 Map을 toString method를 통해 JSON으로 변경
    String jsonStr = Util.json.toString(wiseSaying.toMap());
    // json 형식 파일로 저장
    Util.file.set(getRowFilePath(wiseSaying.getId()), jsonStr);

    return wiseSaying;
  }

  public List<WiseSaying> findAll() {
      try {
          // "db/test/wiseSaying" 디렉토리를 탐색하여 모든 파일 경로를 스트림으로 가져옴
          return Files.walk(Path.of(getTableDirPath()))
                         // 정규 파일만 필터링 (디렉토리 제외)
                         .filter(Files::isRegularFile)
                         // 파일 이름이 숫자.json 형식인지 확인 (예: 1.json, 2.json)
                         .filter(path -> path.getFileName().toString().matches("\\d+\\.json"))
                         // 각 파일 경로에서 JSON 문자열을 읽어옴
                         .map(path -> Util.file.get(path.toString(), ""))
                         // JSON 문자열을 Map<String, Object>로 변환
                         .map(jsonString -> Util.json.toMap(jsonString))
                         // Map 데이터를 기반으로 WiseSaying 객체 생성
                         .map(map -> new WiseSaying(map))
                         // 스트림을 List<WiseSaying>로 변환
                         .toList();
      } catch (NoSuchFileException e) {
          // 디렉토리가 없을 경우 빈 리스트 반환
          return List.of();
      } catch (IOException e) {
          // 입출력 예외가 발생하면 런타임 예외로 변환하여 호출자에게 전달
          throw new RuntimeException(e);
      }
  }

  public boolean deleteById(int id) {
    return Util.file.delete(getRowFilePath(id));
  }

    public Optional<WiseSaying> findById(int id) {
      String filePath = getRowFilePath(id);

      if (Util.file.notExists(filePath)) {
        return Optional.empty();
      }

      String jsonStr = Util.file.get(filePath, "");

      if (jsonStr.isEmpty()) {
        return Optional.empty();
      }
      Map<String, Object> wiseSayingMap = Util.json.toMap(jsonStr);

      return Optional.of(new WiseSaying(wiseSayingMap));
    }
}