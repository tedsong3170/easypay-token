package song.pg.token.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.card.PaymentMethodCardInfo;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.KnownException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class PaymentTokenController
{
  private final PaymentTokenService paymentTokenService;

  @PostMapping("/verify/v1")
  public ResponseEntity<CommonResponse<PaymentMethodCardInfo>> verifyToken()
  {
    log.info("토큰 검증 시작");

    try
    {
      return ResponseEntity.ok()
        .body(paymentTokenService.verifyToken());
    }
    catch (KnownException e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(e.getCode(), e.getMessage(), null));
    }
    catch (Exception e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(ExceptionEnum.UNKNOWN_ERROR.getCode(), ExceptionEnum.UNKNOWN_ERROR.getMessage(), null));
    }
  }
}
