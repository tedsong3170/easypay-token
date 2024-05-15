package song.pg.token.method;

import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.ResponsePaymentMethod;
import song.pg.token.models.payment.method.card.RequestPaymentMethodCardRegister;

import java.util.List;

public interface PaymentMethodService
{
  /**
   * 등록된 결제수단 조회
   * @param di
   * @return
   */
  List<ResponsePaymentMethod> getPaymentMethods(
    final String di
  );

  /**
   * 카드정보 등록
   * @param di
   * @param mid
   * @param requestPaymentMethodCardRegister
   * @return
   */
  CommonResponse<ResponsePaymentMethod> registerCardInfo(
    final String di,
    final String mid,
    final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  );
}
