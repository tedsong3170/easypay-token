package song.pg.token.token;

import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.card.PaymentMethodCardInfo;

public interface PaymentTokenService
{
  CommonResponse<String> createToken(
    final String di,
    final String mid,
    final String paymentId,
    final String paymentMethodId,
    final Long expectAmount
  );

  CommonResponse<PaymentMethodCardInfo> verifyToken();
}
