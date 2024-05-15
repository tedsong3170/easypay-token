package song.pg.token.token;

import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.card.PaymentMethodCardInfo;
import song.pg.token.models.payment.token.PaymentTokenInfo;

public interface PaymentTokenService
{
  /**
   * 1회용 토큰 생성
   * @param di
   * @param mid
   * @param paymentId
   * @param paymentMethodId
   * @param expectAmount
   * @return
   */
  CommonResponse<PaymentTokenInfo> createToken(
    final String di,
    final String mid,
    final String paymentId,
    final String paymentMethodId,
    final Long expectAmount
  );

  /**
   * 토큰 검증 및 결제수단 정보 응답
   * @return
   */
  CommonResponse<PaymentMethodCardInfo> verifyToken();
}
