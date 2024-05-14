package song.pg.token.method.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.pg.token.method.PaymentMethodService;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.PaymentMethodInfoEntity;
import song.pg.token.models.payment.method.PaymentMethodType;
import song.pg.token.models.payment.method.ResponsePaymentMethod;
import song.pg.token.models.payment.method.card.RequestPaymentMethodCardRegister;
import song.pg.token.repository.PaymentMethodRepository;
import song.pg.token.utils.Aes256Util;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.JsonUtil;
import song.pg.token.utils.KnownException;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService
{
  private final PaymentMethodRepository paymentMethodRepository;
  private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{4}$");

  @Override
  public List<ResponsePaymentMethod> getPaymentMethods(final String di)
  {
    return paymentMethodRepository.findAllByCustomerDi(di).stream()
      .map(ResponsePaymentMethod::new)
      .toList();
  }

  @Override
  public CommonResponse<ResponsePaymentMethod> registerCardInfo(final String di,
                                                                final String mid,
                                                                final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  )
  {
    if (!CARD_NUMBER_PATTERN.matcher(
        Aes256Util.decrypt(requestPaymentMethodCardRegister.getCardNumber3(), di.substring(0, 32))
      ).find() ||
      !CARD_NUMBER_PATTERN.matcher(
        Aes256Util.decrypt(requestPaymentMethodCardRegister.getCardNumber4(), di.substring(0, 32))
      ).find()
    )
    {
      throw new KnownException(ExceptionEnum.INVALID_CARD_NUMBER);
    }

    List<PaymentMethodInfoEntity> registeredMethods = paymentMethodRepository.findAllByCustomerDi(di);

    if (registeredMethods.stream().anyMatch(registered ->
      registered
        .getNickName()
        .equals(requestPaymentMethodCardRegister.getNickName())
    )
    )
    {
      throw new KnownException(ExceptionEnum.ALREADY_EXIST_NICKNAME);
    }

    if (registeredMethods.stream()
      .map(PaymentMethodInfoEntity::getCardInfo)
      .map(cardInfo -> JsonUtil.fromJson(cardInfo, RequestPaymentMethodCardRegister.class))
      .anyMatch(data -> {
        RequestPaymentMethodCardRegister registeredCard = (RequestPaymentMethodCardRegister) data;

        return registeredCard.getCardNumber1().equals(requestPaymentMethodCardRegister.getCardNumber1()) &&
                registeredCard.getCardNumber2().equals(requestPaymentMethodCardRegister.getCardNumber2()) &&
                registeredCard.getCardNumber3().equals(requestPaymentMethodCardRegister.getCardNumber3()) &&
                registeredCard.getCardNumber4().equals(requestPaymentMethodCardRegister.getCardNumber4());
      })
    )
    {
      throw new KnownException(ExceptionEnum.ALREADY_EXIST_PAYMENT);
    }

    PaymentMethodInfoEntity paymentMethodInfoEntity = new PaymentMethodInfoEntity(
      di,
      mid,
      PaymentMethodType.CARD.name(),
      requestPaymentMethodCardRegister,
      requestPaymentMethodCardRegister.getNickName()
    );

    paymentMethodRepository.save(paymentMethodInfoEntity);

    return new CommonResponse<>(
      "200",
      "성공",
      new ResponsePaymentMethod(paymentMethodInfoEntity)
    );
  }
}
