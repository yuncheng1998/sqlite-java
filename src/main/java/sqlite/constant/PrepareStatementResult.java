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
  PREPARE_SYNTAX_ERROR

}
