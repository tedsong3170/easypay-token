package song.pg.token.token.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.token.PaymentTokenEntity;
import song.pg.token.repository.PaymentTokenRepository;
import song.pg.token.token.PaymentTokenService;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.JwtUtil;
import song.pg.token.utils.KnownException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentTokenServiceImpl implements PaymentTokenService
{
  private final PaymentTokenRepository paymentTokenRepository;
  private final JwtUtil JwtUtil;
  @Override
  public CommonResponse<String> createToken(final String di,
                                            final String mid,
                                            final String paymentId,
                                            final String paymentMethodId,
                                            final Long expectAmount
  )
  {
    PaymentTokenEntity paymentTokenEntity = paymentTokenRepository.findByPaymentId(paymentId);

    if (paymentTokenEntity != null)
    {
      throw new KnownException(ExceptionEnum.ALREADY_EXIST_TOKEN);
    }

    paymentTokenEntity = new PaymentTokenEntity(
      JwtUtil.generate(
        paymentId,
        paymentMethodId,
        expectAmount
      ),
      paymentMethodId,
      paymentId,
      new BigDecimal(expectAmount)
    );

    paymentTokenRepository.save(paymentTokenEntity);

    return new CommonResponse<>(
      "200",
      "성공",
      paymentTokenEntity.getToken()
    );
  }
}
