package song.pg.token.models.payment.method.card;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentMethodCardInfo
{
  private String cardNumber1;

  private String cardNumber2;

  private String cardNumber3;

  private String cardNumber4;

  private int expireYear;

  private int expireMonth;

  private int cvc;

  private int password;

  private String cardHolderName;

  public PaymentMethodCardInfo(final RequestPaymentMethodCardRegister cardInfo)
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
  }

  public PaymentMethodCardInfo(final Map<String, Object> data)
  {
    this.cardNumber1 = (String) data.get("cardNumber1");
    this.cardNumber2 = (String) data.get("cardNumber2");
    this.cardNumber3 = (String) data.get("cardNumber3");
    this.cardNumber4 = (String) data.get("cardNumber4");
    this.expireYear = Integer.parseInt((String) data.get("expireYear"));
    this.expireMonth = Integer.parseInt((String) data.get("expireMonth"));
    this.cvc = Integer.parseInt((String) data.get("cvc"));
    this.password = Integer.parseInt((String) data.get("password"));
    this.cardHolderName = (String) data.get("cardHolderName");
  }
}
