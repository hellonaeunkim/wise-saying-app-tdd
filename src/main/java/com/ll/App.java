package com.ll;

import com.ll.domain.system.controller.SystemController;
import com.ll.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
  private final Scanner scanner;
  private final SystemController systemController;
  private final WiseSayingController wiseSayingController;

  public App(Scanner scanner) {
    this.scanner = scanner;
    this.systemController = new SystemController();
    this.wiseSayingController = new WiseSayingController(scanner);
  }

  public void run() {
    System.out.println("==명언 앱==");

    while (true) {
      System.out.println("명령) ");
      String cmd = scanner.nextLine();

      Command command = new Command(cmd);

      switch (command.getActionName()) {
        case "종료":
          systemController.actionExit();
          return;
        case "등록":
          wiseSayingController.actionAdd();
          break;
        case "목록":
          wiseSayingController.actionList();
          break;
        case "삭제":
          wiseSayingController.actionDelete(command);
          break;
        case "수정":
          wiseSayingController.actionModify(command);
          break;
        default:
          System.out.println("올바른 명령어가 아닙니다.");
      }
    }
  }
}

