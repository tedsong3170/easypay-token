package song.pg.token.token;

import song.pg.token.models.common.CommonResponse;

public interface PaymentTokenService
{
  CommonResponse<String> createToken(
    final String di,
    final String mid,
    final String paymentId,
    final String paymentMethodId,
    final Long expectAmount
  );
}
