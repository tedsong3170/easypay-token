package song.pg.token.models.payment.token;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentTokenVo implements Serializable
{
  private static final long serialVersionUID = -6137794869034989288L;

  //토큰
  private String token;

  // 결제수단ID
  private String paymentMethodId;

  // 결제ID
  private String paymentId;

  // 결제예상금액
  private BigDecimal expectAmount;

  // 발급일시
  private LocalDateTime issueAt;

  // 유효성확인일시
  private LocalDateTime verifyAt;

  public PaymentTokenVo(PaymentTokenEntity entity)
  {
    // 결제토큰
    this.token = entity.getToken();
    this.paymentMethodId = entity.getPaymentMethodId();
    this.paymentId = entity.getPaymentId();
    this.expectAmount = entity.getExpectAmount();
    this.issueAt = entity.getIssueAt();
    this.verifyAt = entity.getVerifyAt();
  }
}
