package song.pg.token.models.payment.method.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import song.pg.payment.method.card.create.v1.proto.MethodCardCreateV1;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestPaymentMethodCardRegister {
  @NotBlank(message = "카드번호는 필수값입니다.")
  private String cardNumber1;

  @NotBlank(message = "카드번호는 필수값입니다.")
  private String cardNumber2;

  @NotBlank(message = "카드번호는 필수값입니다.")
  private String cardNumber3;

  @NotBlank(message = "카드번호는 필수값입니다.")
  private String cardNumber4;

  @NotNull(message = "만료년도는 필수값입니다.")
  private int expireYear;

  @NotNull(message = "만료월은 필수값입니다.")
  private int expireMonth;

  @NotNull(message = "CVC는 필수값입니다.")
  private int cvc;

  @NotNull(message = "비밀번호는 필수값입니다.")
  private int password;

  @NotBlank(message = "카드소유자명은 필수값입니다.")
  private String cardHolderName;

  @NotBlank(message = "별칭은 필수값입니다.")
  private String nickName;

  public RequestPaymentMethodCardRegister(final MethodCardCreateV1.CardInfo cardInfo)
  {
    this.cardNumber1 = cardInfo.getCardNumber1();
    this.cardNumber2 = cardInfo.getCardNumber2();
    this.cardNumber3 = cardInfo.getCardNumber3();
    this.cardNumber4 = cardInfo.getCardNumber4();
    this.expireYear = cardInfo.getExpireYear();
    this.expireMonth = cardInfo.getExpireMonth();
    this.cvc = cardInfo.getCvc();
    this.password = cardInfo.getPassword();
    this.cardHolderName = cardInfo.getCardHolderName();
    this.nickName = cardInfo.getNickName();
  }
}
