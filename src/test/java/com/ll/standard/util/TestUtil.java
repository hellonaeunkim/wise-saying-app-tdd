package com.ll.standard.util;

import java.io.*;
import java.util.Scanner;

//Input, Output 키보드와 모니터가 아닌 메모리 기반 스트림으로 대체하여 자동화된 테스트
public class TestUtil {

  public static Scanner getScanner(String input) {
    return new Scanner(input);
  }

  public static ByteArrayOutputStream setOutToByteArray() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));

    return output;
  }

  public static void clearSetOutToByteArray(ByteArrayOutputStream byteArrayOutputStream) {
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

    try {
      byteArrayOutputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
