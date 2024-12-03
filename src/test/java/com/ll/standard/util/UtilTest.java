package com.ll.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {
    @Test
    @DisplayName("파일을 생성할 수 있다.")
    public void t1() {
        String filePath = "temp/test.txt";

        Util.file.touch(filePath);

        assertThat(
                Util.file.exists(filePath)
        ).isTrue();

        Util.file.delete(filePath);
    }

    @Test
    @DisplayName("파일의 내용을 수정할 수 있고, 읽을 수 있다.")
    public void t2() {
        String filePath = "temp/test.txt";

        Util.file.set(filePath, "내용");

        assertThat(
                Util.file.get(filePath, "")
        ).isEqualTo("내용");
        Util.file.delete(filePath);
    }

    @Test
    @DisplayName("파일을 삭제할 수 있다.")
    public void t3 () {
        String filePath = "temp/test.txt";

        Util.file.touch(filePath);
        Util.file.delete(filePath);

        assertThat(
                Util.file.notExists(filePath)
        ).isTrue();
    }
}
