package sqlite;

import lombok.Data;

import static sqlite.constant.Constant.PAGE_SIZE;
import static sqlite.constant.Constant.TABLE_MAX_PAGES;

/**
 * 数据表
 * @author yuncheng
 */
@Data
public class Table {

  /**
   * 行数
   */
  private int rows;

  /**
   * 容量, 每页都是
   */
  private byte[] capacity;

  /**
   * 表由页组成，每张表有 100 页
   */
  public Table() {
    this.rows = 0;
    this.capacity = new byte[TABLE_MAX_PAGES * PAGE_SIZE];
  }

  /**
   * 行数增加一行
   */
  void addRow() {
    this.rows++;
  }



}
