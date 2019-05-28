package sqlite.constant;

/**
 * 预处理语句结果
 * @author yuncheng
 */
public enum PrepareStatementResult {
  /**
   * 成功
   */
  PREPARE_SUCCESS,
  /**
   * 失败
   */
  PREPARE_UNRECOGNIZED_STATEMENT,
  /**
   * 语法错误
   */
  PREPARE_SYNTAX_ERROR,

  /**
   * 字符串超出长度
   */
  PREPARE_STRING_TOO_LONG,

  /**
   * ID 必须为正数
   */
  PREPARE_NEGATIVE_ID
}
