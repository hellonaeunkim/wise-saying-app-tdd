package com.ll;

public class Command {
    private final String actionName;
    private final String queryString;

    public Command (String cmd) {
        String[] cmdBits = cmd.trim().split("\\?");
        this.actionName = cmdBits[0];
        this.queryString = cmdBits.length > 1 ? cmdBits[1] : "";
    }

    public String getActionName() {
        return actionName;
    }


}
