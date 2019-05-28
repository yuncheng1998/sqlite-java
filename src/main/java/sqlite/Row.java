package sqlite;

import sqlite.constant.Constant;

import java.io.Serializable;

/**
 * @author yuncheng
 */

public class Row implements Serializable {
  /**
   * id
   */
  private int id;
  /**
   * username
   */
  private char[] username;
  /**
   * email
   */
  private char[] email;

  public Row(int id, char[] username, char[] email) {
    this.username = new char[Constant.COLUMN_USERNAME_SIZE];
    this.email = new char[Constant.COLUMN_EMAIL];
    this.id = id;
    this.setUsername(username);
    this.setEmail(email);
  }

  public Row() {
    this.username = new char[Constant.COLUMN_USERNAME_SIZE];
    this.email = new char[Constant.COLUMN_EMAIL];
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public char[] getUsername() {
    return username;
  }

  public void setUsername(char[] username) {
    System.arraycopy(username, 0, this.username, 0, username.length);
  }

  public char[] getEmail() {
    return email;
  }

  public void setEmail(char[] email) {
    System.arraycopy(email, 0, this.email, 0, email.length);
  }
}
