package song.pg.token.models.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RequestTokenVerify implements Serializable
{
  private static final long serialVersionUID = -671106201416150628L;

  private String paymentId;
  private String mid;
  private BigDecimal amount;
  private BigDecimal taxFreeAmount;
  private String orderId;
  private String orderName;
  private Integer installmentMonth;
}
