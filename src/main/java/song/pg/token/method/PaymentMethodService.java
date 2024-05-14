package song.pg.token.method;

import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.ResponsePaymentMethod;
import song.pg.token.models.payment.method.card.RequestPaymentMethodCardRegister;

import java.util.List;

public interface PaymentMethodService
{
  List<ResponsePaymentMethod> getPaymentMethods(
    final String di
  );

  CommonResponse<ResponsePaymentMethod> registerCardInfo(
    final String di,
    final String mid,
    final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  );
}
