package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private final String actionName;
    private final Map<String, String> params;

    public Command(String cmd) {
        this.params = new HashMap<>();

        // 입력된 명령어를 "?" 기준으로 나누어 actionName과 queryString으로 분리
        String[] cmdBits = cmd.trim().split("\\?", 2);
        // "?" 이전 부분은 actionName으로 저장
        this.actionName = cmdBits[0].trim();
        // 만약 "?" 이후의 파라미터가 없는 경우 그대로 종료
        if (cmdBits.length == 1) return;
        // "?" 이후의 파라미터 문자열
        String queryString = cmdBits[1].trim();

        if (queryString.isEmpty()) return;
        // "&"로 나눠 각 파라미터를 추출 (예: "id=10&name=Alice")
        String[] params = queryString.split("&");
        // 각 파라미터를 "=" 기준으로 나눠 키와 값을 맵에 저장
        for (String param : params) {
            String[] paramBits = param.split("=", 2); // "id=10" -> ["id", "10"]
            this.params.put(paramBits[0], paramBits[1]); // 키-값 쌍으로 맵에 저장
        }

    }

    // 액션 이름 반환 (예: "삭제", "등록")
    public String getActionName() {
        return actionName;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    // Overloading
    public String getParam(String key, String defaultValue) {
        return params.getOrDefault(key, defaultValue);
    }

    public int getParamAsInt(String key, int defaultValue) {
        String value = getParam(key);

        if (value == null) return defaultValue;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
