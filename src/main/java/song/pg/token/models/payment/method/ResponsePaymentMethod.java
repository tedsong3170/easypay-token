package song.pg.token.models.payment.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponsePaymentMethod implements Serializable
{
  private static final long serialVersionUID = -5436426056768189573L;

  private String id;
  private PaymentMethodType method;
  private String nickName;

  public ResponsePaymentMethod(PaymentMethodInfoEntity entity)
  {
    this.id = entity.getPaymentMethodId();
    this.method = entity.getMethod().equals("CARD") ? PaymentMethodType.CARD : null;
    this.nickName = entity.getNickName();
  }
}

