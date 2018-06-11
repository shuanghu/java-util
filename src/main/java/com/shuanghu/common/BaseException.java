package com.shuanghu.common;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 1L;
  // 国际化的参数
  private Object[] args;
  // 国际化的msgKey
  private String msgKey;
  // Http返回状态码
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public BaseException(Throwable cause, String msgKey, HttpStatus httpStatus, Object... obj) {
    super(cause);
    this.msgKey = msgKey;
    this.httpStatus = httpStatus;
    args = obj;
  }
  public BaseException(String msgKey, HttpStatus httpStatus, Object... obj) {
    super(msgKey);
    this.msgKey = msgKey;
    this.httpStatus = httpStatus;
    args = obj;
  }

  public BaseException(String msgKey, Object... obj){
    super(msgKey);
    this.msgKey = msgKey;
    args = obj;
  }

  public BaseException(Throwable cause, String msgKey, Object... obj) {
    super(msgKey, cause);
    this.msgKey = msgKey;
    args = obj;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  public String getMsgKey() {
    return msgKey;
  }

  public void setMsgKey(String msgKey) {
    this.msgKey = msgKey;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
