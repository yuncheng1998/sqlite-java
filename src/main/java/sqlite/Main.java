package sqlite;

import sqlite.constant.ExecuteResult;
import sqlite.constant.MetaCommandResult;
import sqlite.constant.PrepareStatementResult;
import sqlite.constant.Statement;
import java.util.Scanner;
import static sqlite.StatementType.STATEMENT_INSERT;
import static sqlite.constant.Constant.*;
import static sqlite.constant.PrepareStatementResult.*;
import static sqlite.utils.RowUtils.deserialize;
import static sqlite.utils.RowUtils.serialize;

/**
 * @author yuncheng
 */
public class Main {

  public static void main(String[] args) {

    doMethod();
  }

  private static void doMethod() {
    Table table = new Table();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String inputLine = getInputLine(scanner);

      // 元命令以.开头
      if (inputLine.startsWith(PRE_META_COMMAND)) {
        switch (doMetaCommand(inputLine, table)) {
          case META_COMMAND_SUCCESS:
            continue;
          case META_COMMAND_UNRECOGNIZED_COMMAND:
            System.out.println("This is not a meta command");
            continue;
            default:break;
        }
      }
      // 其他命令
      Statement statement = new Statement();
      // 语句预处理
      switch (prepareStatement(inputLine, statement)) {
        case PREPARE_SUCCESS:
          break;
        case PREPARE_NEGATIVE_ID:
          System.out.println("ID must be positive");
          continue;
        case PREPARE_STRING_TOO_LONG:
          System.out.println("String is too long");
          continue;
        case PREPARE_SYNTAX_ERROR:
          System.out.println("Syntax error. Could not parse statement");
          continue;
        case PREPARE_UNRECOGNIZED_STATEMENT:
          System.out.println("Unrecognized");
          continue;
          default:
      }
      switch (executeStatement(statement, table)) {
        case EXECUTE_SUCCESS:
          System.out.println("executed!");
          break;
        case EXECUTE_TABLE_FULL:
          System.out.println("Error: TABLE FULL");
          break;
          default:break;
      }
    }
  }

  /**
   * 执行语句
   * @param statement 执行的语句
   * @param table 执行语句的表
   * @return 执行结果
   */
  private static ExecuteResult executeStatement(Statement statement, Table table) {
    switch (statement.getType()) {
      case STATEMENT_INSERT:
        System.out.println("insert");
        return executeInsert(statement, table);
      case STATEMENT_SELECT:
        System.out.println("select");
        return executeSelect(statement, table);
        default:
          System.out.println("no");
          return ExecuteResult.EXECUTE_TABLE_FULL;
    }
  }

  /**
   * 执行SELECT 语句
   * @param statement 语句
   * @param table 表
   * @return 结果
   */
  private static ExecuteResult executeSelect(Statement statement, Table table) {
    Row row = null;
    for (int i = 0; i < table.getRows(); i = i + ROW_SIZE) {
      row = deserialize(table, i);
      System.out.println(row);
    }
    return ExecuteResult.EXECUTE_SUCCESS;

  }




  /**
   * 查找表中的槽位，capacity的下标
   * @param table 表
   * @return 槽位编号
   */
  public static int getSlot(Table table) {
    // 第几页 = 表现有的行数 / 每页最大行数
    int numberOfPages = table.getRows() / ROWS_PER_PAGES;
    // 拿到对应的页
    int offsetOfRow = table.getRows() % ROWS_PER_PAGES;
    int offsetOfByte = offsetOfRow * ROW_SIZE;
    return numberOfPages * PAGE_SIZE + offsetOfByte;
  }

  /**
   * 执行INSERT语句
   * @param statement 语句
   * @param table 表
   * @return 结果
   */
  private static ExecuteResult executeInsert(Statement statement, Table table) {
    if (table.getRows() >= TABLE_MAX_ROWS) {
      return ExecuteResult.EXECUTE_TABLE_FULL;
    } else {
      Row row = statement.getRow2Insert();
      // 序列化到table中
      serialize(row, table, getSlot(table));
      table.addRow();
      return ExecuteResult.EXECUTE_SUCCESS;
    }
  }

  /**
   * 对语句进行预处理
   * INSERT语句: 调用INSERT预处理方法
   * SELECT语句: 直接
   * @param inputLine 输入
   * @param statement 语句
   * @return 预处理结果
   */
  private static PrepareStatementResult prepareStatement(String inputLine, Statement statement) {
    if (inputLine.toUpperCase().startsWith(INSERT)) {
      return prepareInsert(inputLine, statement);
    }
    if (inputLine.toUpperCase().startsWith(SELECT)) {
      statement.setType(StatementType.STATEMENT_SELECT);
      return PREPARE_SUCCESS;
    }
    return PREPARE_UNRECOGNIZED_STATEMENT;
  }

  /**
   * 对INSERT语句预处理
   * @param inputLine 输入
   * @param statement 语句
   * @return 处理结果
   */
  private static PrepareStatementResult prepareInsert(String inputLine, Statement statement) {
    statement.setType(STATEMENT_INSERT);
    String[] strings = inputLine.split("\\s+");
    if (strings.length != 4) {
      return PREPARE_SYNTAX_ERROR;
    }
    int id = Integer.valueOf(strings[1]);
    char[] username = strings[2].toCharArray();
    char[] email = strings[3].toCharArray();

    if (id < 0) {
      return PREPARE_NEGATIVE_ID;
    }
    if (username.length > COLUMN_USERNAME_SIZE || email.length > COLUMN_EMAIL) {
      return PREPARE_STRING_TOO_LONG;
    }
    Row row = new Row(id, username, email);
    statement.setRow2Insert(row);
    return PREPARE_SUCCESS;

  }

  private static MetaCommandResult doMetaCommand(String inputLine, Table table) {
    if (EXIT_COMMAND.equals(inputLine.trim())) {
      System.out.println("bye-bye");
      System.exit(0);
    }

    return MetaCommandResult.META_COMMAND_UNRECOGNIZED_COMMAND;

  }

  private static String getInputLine(Scanner scanner) {
    System.out.print("sqlite-java > ");
    return scanner.nextLine();
  }

}
