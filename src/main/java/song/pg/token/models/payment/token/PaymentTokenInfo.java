package song.pg.token.models.payment.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTokenInfo implements Serializable
{
  private static final long serialVersionUID = 1390408599698203773L;

  private String token;
  private String approvalUrl;
}
