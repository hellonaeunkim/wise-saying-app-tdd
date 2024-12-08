package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
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

  private String getLastIdPath() {
      return getTableDirPath() + "/lastId.txt";
    }

  public WiseSaying save(WiseSaying wiseSaying) {
    // 메모리 저장 특성상 새 객체가 아니라면 딱히 할게 없다.
        if (!wiseSaying.isNew()) {
            return wiseSaying;
        }

        int id = getLastId() + 1;

        wiseSaying.setId(id);

        // toMap() method를 통해 wiseSaying을 Map으로 변환
        // json 형식으로 저장될 Map을 toString method를 통해 JSON으로 변경
        String jsonStr = Util.json.toString(wiseSaying.toMap());
        // json 형식 파일로 저장
        Util.file.set(getRowFilePath(wiseSaying.getId()), jsonStr);

        setLastId(id);

        return wiseSaying;
        }

  public List<WiseSaying> findAll() {
      try {
          // getTableDirPath() 디렉토리 안에서 fileNameRegex 패턴을 가진 파일들만 반복
          return Util.file.walkRegularFiles(
                  getTableDirPath(), "\\d+\\.json"
        )        // 각 파일 경로에서 JSON 문자열을 읽어옴
                 .map(path -> Util.file.get(path.toString(), ""))
                 // JSON 문자열을 Map<String, Object>로 변환
                 .map(Util.json::toMap)
                 // Map 데이터를 기반으로 WiseSaying 객체 생성
                 .map(WiseSaying::new)
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

        public int getLastId() {
            return Util.file.getAsInt(getLastIdPath(),0);
        }

        public void setLastId(int id) {
            Util.file.set(getLastIdPath(), id);
        }
}