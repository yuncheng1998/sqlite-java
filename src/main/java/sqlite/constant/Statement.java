package sqlite.constant;

import lombok.Data;
import sqlite.Row;
import sqlite.StatementType;

/**
 * 语句
 * @author yuncheng
 */
@Data
public class Statement {

  /**
   * 语句的类型
   */
  StatementType type;
  /**
   * 用来插入的行
   */
  Row row2Insert;
}
