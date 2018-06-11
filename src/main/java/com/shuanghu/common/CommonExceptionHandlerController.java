package com.shuanghu.common;

import com.shuanghu.common.response.ExceptionResponse;
import com.shuanghu.common.response.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 异常公共处理
 */
@ControllerAdvice
public class CommonExceptionHandlerController {
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseBody
  ResponseEntity handleDuplicateException(DuplicateKeyException e){
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
        .body(new ExceptionResponse(I18nUtils.getMessage("com.shuanghu.common.dao.duplicateName"), e.getMessage()));
  }

  @ExceptionHandler(BaseException.class)
  @ResponseBody
  ResponseEntity handleOverseaException(BaseException e){
    return ResponseEntity.status(e.getHttpStatus())
        .body(new ExceptionResponse(I18nUtils.getMessage(e), e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  ResponseEntity handleAllException(Exception e) {
    /**
     * 所有异常
     */
    LOGGER.error("INTERNAL_ERROR", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ExceptionResponse(
            I18nUtils.getMessage("com.shuanghu.common.internalServerError"), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseBody
  public ResponseEntity handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    /**
     * 必选参数类型转换错误
     */
    LOGGER.error("MethodArgumentTypeMismatchException", ex);

    // TODO enum
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ExceptionResponse(I18nUtils
            .getMessage("com.shuanghu.common.param.errorType", ex.getName(),
                ex.getRequiredType().getSimpleName()),
            ex.getMessage()));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseBody
  public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
    /**
     * 必选参数未传值
     */
    LOGGER.error("MissingServletRequestParameterException", ex);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ExceptionResponse(I18nUtils.getMessage("com.shuanghu.common.param.required",
            ex.getParameterName(), ex.getParameterType()),
            ex.getMessage()));
  }
}
