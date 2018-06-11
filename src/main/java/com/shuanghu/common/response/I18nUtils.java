package com.shuanghu.common.response;

import com.shuanghu.common.BaseException;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.StringUtils;

public class I18nUtils {
  private static Logger LOGGER = LoggerFactory.getLogger(I18nUtils.class);

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    I18nUtils.messageSource = messageSource;
  }

  static private MessageSource messageSource;

  /**
   * 国际化
   */
  static public String getMessage(String code, Object... args) {
    return baseResponseTranslation(LocaleContextHolder.getLocale(), code, args);
  }

  static public String getMessage(BaseException baseException) {
    String msg = null;

    if (!StringUtils.isEmpty(baseException.getMsgKey())) {
      try {
        msg = messageSource.getMessage(baseException.getMsgKey(), baseException.getArgs()
            , LocaleContextHolder.getLocale());
      } catch (Exception e) {
        LOGGER.error("Translation baseResponse error!", e);
        return baseException.getMsgKey();
      }

      LOGGER.debug("code: {}, msg: {}", baseException.getArgs(), msg);
    }

    return msg;
  }

  /**
   * 国际化
   */
  private static String baseResponseTranslation(Locale local, String code, Object... args) {
    String msg = null;

    if (!StringUtils.isEmpty(code)) {
      try {
        msg = messageSource.getMessage(code, args, local);
      } catch (Exception e) {
        LOGGER.error("Translation baseResponse error!", e);
        return code;
      }

      LOGGER.debug("code: {}, msg: {}", code, msg);
    }

    return msg;
  }

  public static MessageSourceResolvable toMsgKey(String[] keys){
    if (keys==null){
      return null;
    }
    MessageSourceResolvable messageSourceResolvable = null;
    for (String name : keys) {
      if (messageSourceResolvable == null) {
        messageSourceResolvable = new DefaultMessageSourceResolvable(name);
      } else {
        messageSourceResolvable = new DefaultMessageSourceResolvable(messageSourceResolvable);
      }
    }

    return messageSourceResolvable;
  }

}
