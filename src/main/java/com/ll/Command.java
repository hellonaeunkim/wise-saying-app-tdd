package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private final String actionName;
    private final Map<String, String> params;

    public Command(String cmd) {
        this.params = new HashMap<>();
        String[] cmdBits = cmd.trim().split("\\?", 2);
        this.actionName = cmdBits[0].trim();

        if (cmdBits.length == 1) return;

        String queryString = cmdBits[1].trim();

        if (queryString.isEmpty()) return;

        String[] params = queryString.split("&");

        for (String param : params) {
            String[] paramBits = param.split("=", 2);
            this.params.put(paramBits[0], paramBits[1]);
        }

    }

    public String getActionName() {
        return actionName;
    }

    public String getParam(String key) {
        return params.get(key);
    }

        // Overloading
        public String getParam (String key, String defaultValue){
            return params.getOrDefault(key, defaultValue);
        }

    public int getParamAsInt(String key, int defaultValue) {
        String value = getParam(key);

        if ( value == null ) return defaultValue;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
