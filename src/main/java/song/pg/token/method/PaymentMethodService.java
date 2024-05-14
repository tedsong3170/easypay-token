package song.pg.token.method;

import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.ResponsePaymentMethod;

import java.util.List;

public interface PaymentMethodService
{
  List<ResponsePaymentMethod> getPaymentMethods(
    final String di
  );
}
