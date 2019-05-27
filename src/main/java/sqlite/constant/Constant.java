package sqlite.constant;

/**
 * 常量
 * @author yuncheng
 */
public interface Constant {

  /**
   * 元命令的前缀
   */
  String PRE_META_COMMAND = ".";

  /**
   * 元命令
   */
  String EXIT_COMMAND = ".exit";

  /**
   * INSERT 关键字
   */
  String INSERT = "INSERT";

  /**
   * SELECT 关键字
   */
  String SELECT = "SELECT";

  /**
   * USERNAME列的字节数
   */
  int COLUMN_USERNAME_SIZE = 32;

  /**
   * EMAIL列的字节数
   */
  int COLUMN_EMAIL = 255;

  /**
   * ID占的字节数
   */
  int ID_SIZE = 4;

  /**
   * 每行占的字节数
   */
  int ROW_SIZE = ID_SIZE + COLUMN_USERNAME_SIZE + COLUMN_EMAIL;

  /**
   * 每张表最多页数
   */
  int TABLE_MAX_PAGES = 100;

  /**
   * 每页大小 4KB
   */
  int PAGE_SIZE = 4096;

  /**
   * 每页的行数
   */
  int ROWS_PER_PAGES = PAGE_SIZE / ROW_SIZE;

  /**
   * 每张表最多的行数
   */
  int TABLE_MAX_ROWS = ROWS_PER_PAGES * TABLE_MAX_PAGES;


}
