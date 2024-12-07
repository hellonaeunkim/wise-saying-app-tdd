package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingFileRepositoryTest {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingFileRepository();

    @BeforeEach
    public void beforeEach() {
        Util.file.rmdir(WiseSayingFileRepository.getTableDirPath());
    }

    @AfterEach
    public void afterEach() {
        Util.file.rmdir(WiseSayingFileRepository.getTableDirPath());
    }

    @Test
    @DisplayName("명언 저장")
    public void t1() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingRepository.save(wiseSaying);

        // 1.json 파일에 명언이 저장되었는지 확인
        String filePath = WiseSayingFileRepository.getRowFilePath(wiseSaying.getId());

        assertThat(
                Util.file.exists(filePath)
        ).isTrue();

        // 파일 읽어와서 Map 형태로 바뀌는지 Test
        String jsonStr = Util.file.get(filePath,"");
        Map<String, Object> wiseSayingMap = Util.json.toMap(jsonStr);
        // WiseSaying 생성자로 Map을 입력
        WiseSaying wiseSayingRestored = new WiseSaying(wiseSayingMap);

        assertThat(wiseSayingRestored).isEqualTo(wiseSaying);
    }

    @Test
    @DisplayName("명언 삭제")
    public void t2() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingRepository.save(wiseSaying);

        wiseSayingRepository.deleteById(wiseSaying.getId());

        String filePath = WiseSayingFileRepository.getRowFilePath(wiseSaying.getId());

        // 1.json 파일이 없는지 확인
        assertThat(
                Util.file.exists(filePath)
        ).isFalse();

    }
}
