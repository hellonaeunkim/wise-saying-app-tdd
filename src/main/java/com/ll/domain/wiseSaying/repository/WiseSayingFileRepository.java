package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
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
      // 현재 wiseSaying 객체가 새로운 객체(기존에 저장된 적 없는)인지 확인
      // isNew()는 일반적으로 id == 0 일 때 true를 반환
      boolean isNew =  wiseSaying.isNew();

      // 새 객체라면 새로운 ID 할당
      // getLastId()로 마지막으로 사용된 ID를 가져와 +1을 해 새로운 ID를 부여
      if (isNew) {
            wiseSaying.setId(getLastId() + 1);
        }

        String jsonStr = wiseSaying.toJsonStr();
        // json 형식 파일로 저장
        Util.file.set(getRowFilePath(wiseSaying.getId()), jsonStr);

      if (isNew) {
          setLastId(wiseSaying.getId());
      }

        return wiseSaying;
        }

  public List<WiseSaying> findAll() {
      try {
          // getTableDirPath() 디렉토리 안에서 fileNameRegex 패턴을 가진 파일들만 반복
          return Util.file.walkRegularFiles(
                  getTableDirPath(), "\\d+\\.json"
        )        // 각 파일 경로에서 JSON 문자열을 읽어옴
                 .map(path -> Util.file.get(path.toString(), ""))
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

          return Optional.of(new WiseSaying(jsonStr));
        }

        public int getLastId() {
            return Util.file.getAsInt(getLastIdPath(),0);
        }

        public void setLastId(int id) {
            Util.file.set(getLastIdPath(), id);
        }
}