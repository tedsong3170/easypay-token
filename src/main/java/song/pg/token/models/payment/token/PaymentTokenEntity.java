package song.pg.token.models.payment.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_token_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTokenEntity
{
  // 결제토큰
  @Id
  @Column(name="token", nullable = false)
  private String token;

  // 결제수단ID
  @Column(name="payment_method_id", nullable = false, columnDefinition = "char")
  private String paymentMethodId;

  // 결제ID
  @Column(name="payment_id", nullable = false, columnDefinition = "char")
  private String paymentId;

  // 결제예상금액
  @Column(name="expect_amount", nullable = false)
  private BigDecimal expectAmount;

  // 발급일시
  @Column(name="issue_at", nullable = false)
  private LocalDateTime issueAt = LocalDateTime.now();

  // 유효성확인일시
  @Column(name="verify_at")
  private LocalDateTime verifyAt;

  public PaymentTokenEntity(
    final String token,
    final String paymentMethodId,
    final String paymentId,
    final BigDecimal expectAmount
  )
  {
    this.token = token;
    this.paymentMethodId = paymentMethodId;
    this.paymentId = paymentId;
    this.expectAmount = expectAmount;
  }
}
