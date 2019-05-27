package sqlite.utils;

import sqlite.Row;
import sqlite.Table;
import sqlite.constant.Constant;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import static sqlite.Main.getSlot;
import static sqlite.constant.Constant.*;

/**
 * 行对象工具类
 * 1. 序列化
 * 2. 反序列化
 * @author yuncheng
 */
public class RowUtils {

  /**
   * 将一行序列化到table中
   * @param row 行，序列化的对象
   * @param table 表，存序列化后对象
   * @param slot 槽位
   */
  public static void serialize(Row row, Table table, int slot) {
    byte[] bytes = new byte[ROW_SIZE];
    System.arraycopy(convertInt2Bytes(row.getId()), 0, bytes, 0, 4);
    System.arraycopy(convertChars2Bytes(row.getUsername()), 0, bytes, ID_SIZE, Constant.COLUMN_USERNAME_SIZE);
    System.arraycopy(convertChars2Bytes(row.getEmail()), 0, bytes, ID_SIZE + COLUMN_USERNAME_SIZE, Constant.COLUMN_EMAIL);
    System.arraycopy(bytes, 0, table.getCapacity(), slot, ROW_SIZE);
  }

  /**
   * 反序列化
   * @param table 表
   * @param slot 槽位
   * @return Row
   */
  public static Row deserialize(Table table, int slot) {
    Row row = new Row();
    byte[] bytes = new byte[ROW_SIZE];
    System.arraycopy(table.getCapacity(), slot, bytes, 0, ROW_SIZE);
    row.setId(convertBytes2Int(Arrays.copyOfRange(bytes, 0, ID_SIZE)));
    row.setUsername(convertBytes2Chars(Arrays.copyOfRange(bytes, ID_SIZE, ID_SIZE + COLUMN_USERNAME_SIZE)));
    row.setEmail(convertBytes2Chars(Arrays.copyOfRange(bytes, ID_SIZE + COLUMN_USERNAME_SIZE, ROW_SIZE)));
    return row;
  }

  public static void main(String[] args) {
    Table table = new Table();
    Row row = new Row();
    char[] userName = {'h', 'b'};
    char[] email = {'@', 'b'};
    row.setId(1);
    row.setUsername(userName);
    row.setEmail(email);
    serialize(row, table, getSlot(table));
    Row r = deserialize(table, 0);

  }



  /**
   * 将int转为低字节在前，高字节在后的byte数组
   * @param n 整数
   * @return byte数组
   */
  private static byte[] convertInt2Bytes(int n) {
    byte[] b = new byte[4];
    b[0] = (byte) (n & 0xff);
    b[1] = (byte) (n >> 8 & 0xff);
    b[2] = (byte) (n >> 16 & 0xff);
    b[3] = (byte) (n >> 24 & 0xff);
    return b;
  }

  /**
   * 将低字节在前，高字节在后的byte数组转为int
   * @param bytes byte数组
   * @return 整数
   */
  private static int convertBytes2Int(byte[] bytes) {
    int n = 0;
    for(int i = 0; i < bytes.length && i < 4; i++){
      int left = i * 8;
      n += (bytes[i] << left);
    }
    return n;
  }

  private static byte[] convertChars2Bytes(char[] chars) {
    return String.valueOf(chars).getBytes();
  }

  private static char[] convertBytes2Chars(byte[] bytes) {
    Charset cs = Charset.forName("UTF-8");
    ByteBuffer bb = ByteBuffer.allocate(bytes.length);
    bb.put(bytes);
    bb.flip();
    CharBuffer cb = cs.decode(bb);
    return cb.array();
  }








}



