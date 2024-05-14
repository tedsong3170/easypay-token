package song.pg.token.models.payment.method;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_method_info")
@Getter
@Setter
public class PaymentMethodInfoEntity
{
  // 결제수단ID
  @Id
  @Column(name="payment_method_id", nullable = false, columnDefinition = "char")
  private String paymentMethodId;

  // 사용자DI
  @Column(name="customer_di", nullable = false, columnDefinition = "char")
  private String customerDi;

  // 가맹점ID
  @Column(name="mid", nullable = false, columnDefinition = "char")
  private String mid;

  // 결제수단 card, account 등
  @Column(name="method", nullable = false)
  private String method;

  // 카드정보
  @Column(name="card_info", columnDefinition = "json")
  private String cardInfo;

  // 등록일
  @Column(name="create_at", nullable = false)
  private LocalDateTime createAt;

  // 삭제일
  @Column(name="deleted_at")
  private LocalDateTime deletedAt;

  // 별명
  @Column(name="nick_name")
  private String nickName;
}
