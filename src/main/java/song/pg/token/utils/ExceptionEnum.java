package song.pg.token.utils;

import lombok.Getter;

@Getter
public enum ExceptionEnum
{
  ALREADY_EXIST_PAYMENT("1001", "이미 결제가 진행중인 주문입니다."),
  UNKNOWN_PAYMENT_METHOD("1002", "알 수 없는 결제 수단입니다."),

  ;

  private final String code;
  private String message;

  ExceptionEnum(String code, String message)
  {
    this.code = code;
    this.message = message;
  }
}
