package song.pg.token.method.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.pg.token.method.PaymentMethodService;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.ResponsePaymentMethod;
import song.pg.token.repository.PaymentMethodRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService
{
  private final PaymentMethodRepository paymentMethodRepository;

  @Override
  public List<ResponsePaymentMethod> getPaymentMethods(final String di)
  {
    return paymentMethodRepository.findAllByCustomerDi(di).stream()
      .map(ResponsePaymentMethod::new)
      .toList();
  }
}
