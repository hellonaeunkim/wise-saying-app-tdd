package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.standard.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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

        Optional<WiseSaying> opWiseSaying = wiseSayingRepository.findById(wiseSaying.getId());

        // 조회된 명언이 원래 객체와 동일한지 확인
        assertThat(
                opWiseSaying.get()
        ).isEqualTo(wiseSaying);
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

    @Test
    @DisplayName("명언 단건조회")
    public void t3() {
        WiseSaying wiseSaying = new WiseSaying(0, "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingRepository.save(wiseSaying);

        Optional<WiseSaying> opWiseSaying = wiseSayingRepository.findById(wiseSaying.getId());

        // 조회된 명언이 원래 객체와 동일한지 확인
        assertThat(
                opWiseSaying.get()
        ).isEqualTo(wiseSaying);

    }
}
