package song.pg.token.token.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import song.pg.token.config.security.OneTimePaymentToken;
import song.pg.token.models.common.CardBinInfoEntity;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.PaymentMethodInfoEntity;
import song.pg.token.models.payment.method.PaymentMethodType;
import song.pg.token.models.payment.method.card.PaymentMethodCardInfo;
import song.pg.token.models.payment.token.PaymentTokenEntity;
import song.pg.token.models.payment.token.PaymentTokenInfo;
import song.pg.token.repository.CardBinInfoRepository;
import song.pg.token.repository.PaymentMethodRepository;
import song.pg.token.repository.PaymentTokenRepository;
import song.pg.token.token.PaymentTokenService;
import song.pg.token.utils.Aes256Util;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.JwtUtil;
import song.pg.token.utils.KnownException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentTokenServiceImpl implements PaymentTokenService
{
  private final PaymentTokenRepository paymentTokenRepository;
  private final PaymentMethodRepository paymentMethodRepository;
  private final CardBinInfoRepository cardBinInfoRepository;
  private final JwtUtil JwtUtil;

  @Override
  public CommonResponse<PaymentTokenInfo> createToken(final String di,
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

    PaymentMethodInfoEntity paymentMethodEntity = paymentMethodRepository.findById(paymentMethodId)
      .orElseThrow(() -> new KnownException(ExceptionEnum.DOSE_NOT_EXIST_PAYMENT_METHOD));

    if (paymentMethodEntity.getMethod().equals(PaymentMethodType.CARD.name()))
    {
      PaymentMethodCardInfo cardInfo = new PaymentMethodCardInfo(paymentMethodEntity.getCardInfo());
      CardBinInfoEntity cardBinInfoEntity = cardBinInfoRepository.findById(cardInfo.getCardNumber1() + cardInfo.getCardNumber2().substring(0, 2))
        .orElseThrow(() -> new KnownException(ExceptionEnum.UNKNOWN_PAYMENT_BIN_INFO));

      return new CommonResponse<>(
        "200",
        "성공",
        new PaymentTokenInfo(
          paymentTokenEntity.getToken(),
          cardBinInfoEntity.getApprovalUrl()
        )
      );

    }

    return new CommonResponse<>(
      "200",
      "성공",
      new PaymentTokenInfo(
        paymentTokenEntity.getToken(),
        ""
      )
    );
  }

  @Override
  public CommonResponse<PaymentMethodCardInfo> verifyToken()
  {
    OneTimePaymentToken tokenInfo = (OneTimePaymentToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    PaymentTokenEntity paymentTokenEntity = paymentTokenRepository.findById(tokenInfo.getPaymentTokenVo().getToken())
      .orElseThrow(() -> new KnownException(ExceptionEnum.DOSE_NOT_EXIST_TOKEN));

    if (paymentTokenEntity.getVerifyAt() != null)
    {
      throw new KnownException(ExceptionEnum.ALREADY_USED_TOKEN);
    }

    PaymentMethodInfoEntity paymentMethodEntity = paymentMethodRepository.findById(tokenInfo.getPaymentTokenVo().getPaymentMethodId())
      .orElseThrow(() -> new KnownException(ExceptionEnum.DOSE_NOT_EXIST_PAYMENT_METHOD));

    PaymentMethodCardInfo cardInfo = new PaymentMethodCardInfo(paymentMethodEntity.getCardInfo());

    cardInfo.setCardNumber3(Aes256Util.decrypt(cardInfo.getCardNumber3(), paymentMethodEntity.getCustomerDi().substring(0, 32)));
    cardInfo.setCardNumber4(Aes256Util.decrypt(cardInfo.getCardNumber4(), paymentMethodEntity.getCustomerDi().substring(0, 32)));

    paymentTokenEntity.setVerifyAt(LocalDateTime.now());

    paymentTokenRepository.save(paymentTokenEntity);

    return new CommonResponse<>(
      "200",
      "성공",
      cardInfo
    );
  }
}
