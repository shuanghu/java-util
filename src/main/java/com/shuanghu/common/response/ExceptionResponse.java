package com.shuanghu.common.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionResponse {
  /**
   * 返回信息，会做国际化处理
   */
  private String message;

  /**
   * 异常信息，不做国际化处理
   */
  private String detailInfo;

  public ExceptionResponse(){
  }

  public ExceptionResponse(String msg, String detailInfo){
    this.message = msg;
    this.detailInfo = detailInfo;
  }
}
