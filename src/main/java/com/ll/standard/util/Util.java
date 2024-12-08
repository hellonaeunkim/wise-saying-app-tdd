package com.ll.standard.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;


public class Util {
    // 파일 관련 유틸리티 클래스
    public static class file {
        private file() {
        } // 유틸리티 클래스의 인스턴스화 방지 (생성자 private)

        // 파일이 존재하지 않으면 빈 파일을 생성
        public static void touch(String filePath) {
            set(filePath, ""); // 빈 내용을 가진 파일 생성
        }

        // 파일이 존재하는지 확인
        public static boolean exists(String filePath) {
            return Files.exists(getPath(filePath));
        }

        // 파일이 존재하지 않는지 확인
        public static boolean notExists(String filePath) {
            return !exists(filePath);
        }

        // 파일에 내용을 설정 (기존 파일이 있으면 덮어씀)
        public static void set(String filePath, String content) {
            Path path = getPath(filePath);
            try {
                writeFile(path, content); // 파일에 내용 쓰기
            } catch (IOException e) {
                handleFileWriteError(path, content, e); // 파일 쓰기 실패 시 처리
            }
        }

        public static void set(String filePath, int content) {
            set(filePath, String.valueOf(content));
        }

        // 파일 내용을 읽어옴. 파일이 없으면 기본값 반환
        public static String get(String filePath, String defaultValue) {
            try {
                return Files.readString(getPath(filePath)); // 파일 내용 읽기
            } catch (IOException e) {
                return defaultValue; // 파일이 없거나 읽기 실패 시 기본값 반환
            }
        }

        public static int getAsInt(String filePath, int defaultValue) {
            // filePath 경로에 있는 파일 내용을 가져와 content에 저장
            // 만약 파일이 없거나 읽을 수 없으면 기본값 ""(빈 문자열) 반환
            String content = get(filePath, "");

            if (content.isBlank()) {
                return defaultValue;
            }

            try {
                return Integer.parseInt(content);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        // 파일 삭제를 처리하는 클래스: 재귀적으로 디렉토리 및 파일 삭제
        private static class FileDeleteVisitor extends SimpleFileVisitor<Path> {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        }

        // 파일 또는 디렉토리를 삭제
        public static boolean delete(String filePath) {
            try {
                Files.walkFileTree(getPath(filePath), new FileDeleteVisitor());
                return true;
            } catch (IOException e) {
                return false; // 삭제 실패 시 false 반환
            }
        }

        // 디렉토리를 생성. 경로가 없으면 생성함
        public static void mkdir(String dirPath) {
            try {
                Files.createDirectories(getPath(dirPath)); // 디렉토리 생성
            } catch (IOException e) {
                throw new RuntimeException("디렉토리 생성 실패: " + dirPath, e); // 실패 시 예외 발생
            }
        }

            // 디렉토리 제거 (파일과 동일한 로직 사용)
            public static boolean rmdir(String dirPath) {
                return delete(dirPath);
            }
            // 내부 유틸리티 메서드: 경로를 `Path` 객체로 변환
            private static Path getPath(String filePath) {
                return Paths.get(filePath);
            }

            // 파일 쓰기 로직. 파일이 없으면 생성, 기존 파일이 있으면 덮어씀
            private static void writeFile(Path path, String content) throws IOException {
                Files.writeString(path, content,
                        StandardOpenOption.CREATE,             // 파일이 없으면 생성
                        StandardOpenOption.TRUNCATE_EXISTING); // 기존 내용 제거
            }

            // 파일 쓰기 실패 시 처리 로직 (부모 디렉토리 생성 후 재시도)
            private static void handleFileWriteError(Path path, String content, IOException e) {
                Path parentDir = path.getParent(); // 부모 디렉토리 경로 가져오기
                if (parentDir != null && Files.notExists(parentDir)) {
                    try {
                        Files.createDirectories(parentDir);  // 부모 디렉토리 생성
                        writeFile(path, content);           // 다시 파일 쓰기 시도
                    } catch (IOException ex) {
                        throw new RuntimeException("파일 쓰기 실패: " + path, ex);
                    }
                } else {
                    throw new RuntimeException("파일 접근 실패: " + path, e);
                }
            }

        public static Stream<Path> walkRegularFiles(String dirPath, String fileNameRegex) throws IOException{
            // "db/test/wiseSaying" 디렉토리를 탐색하여 모든 파일 경로를 스트림으로 가져옴
            return Files.walk(Path.of(dirPath))
                    // 정규 파일만 필터링 (디렉토리 제외)
                    .filter(Files::isRegularFile)
                    // 파일 이름이 숫자.json 형식인지 확인 (예: 1.json, 2.json)
                    .filter(path -> path.getFileName().toString().matches(fileNameRegex));

        }
    }

    public static class json {
        private json() {
        }
        // Map 객체를 JSON 문자열로 변환
        public static String toString(Map<String, Object> map) {
            StringBuilder sb = new StringBuilder();

            sb.append("{");
            sb.append("\n");

            // 각 키-값 쌍을 JSON 형식으로 추가
            map.forEach((key, value) -> {
                sb.append("    ");
                key = "\"" + key + "\""; // 키를 JSON 형식의 문자열로 변환

                // 값이 문자열일 경우 큰따옴표로 감쌈
                if (value instanceof String) {
                    value = "\"" + value + "\"";
                }

                sb.append("%s: %s,\n".formatted(key, value));
            });


            // Map이 비어있지 않으면 마지막 쉼표 제거
            if (!map.isEmpty()) {
                // 마지막 ,와 \n 제거
                sb.delete(sb.length() - 2, sb.length());
            }

            sb.append("\n");
            sb.append("}");

            return sb.toString();
        }

        // JSON 문자열을 Map 객체로 변환
        public static Map<String, Object> toMap(String jsonStr) {
            Map<String, Object> map = new LinkedHashMap<>();

            jsonStr = jsonStr.substring(1, jsonStr.length() - 1); // 중괄호 제거

            String[] jsonStrBits = jsonStr.split(",\n    \""); // 키-값 쌍으로 나누기

            for (String jsonStrBit : jsonStrBits) {
                jsonStrBit = jsonStrBit.trim();

                // 쉼표 제거
                if (jsonStrBit.endsWith(",")) jsonStrBit = jsonStrBit.substring(0, jsonStrBit.length() - 1);

                // 키와 값을 분리
                String[] jsonField = jsonStrBit.split("\": ");
                String key = jsonField[0];
                if (key.startsWith("\"")) key = key.substring(1); // 키의 큰따옴표 제거

                String value = jsonField[1];
                boolean valueIsString = value.startsWith("\"") && value.endsWith("\"");

                if (valueIsString) value = value.substring(1, value.length() - 1);

                // String일 때 value 값
                if (valueIsString) {
                    map.put(key, value);
                }
                // boolean 일 때 value 값
                else if (value.equals("true") || value.equals("false")) {
                    map.put(key, value.equals("true"));
                }
                // 실수 일 때 value 값
                else if (value.contains(".")) {
                    map.put(key, Double.parseDouble(value));
                } else {
                    map.put(key, Integer.parseInt(value));
                }
            }
            return map;

        }
    }
}


