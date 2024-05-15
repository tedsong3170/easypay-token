package song.pg.token.token;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import song.pg.payment.token.create.v1.proto.TokenCreateGrpc;
import song.pg.payment.token.create.v1.proto.TokenCreateV1;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.token.PaymentTokenInfo;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.KnownException;

@GrpcService
@RequiredArgsConstructor
public class PaymentTokenCreateGrpcHandler extends TokenCreateGrpc.TokenCreateImplBase
{
  private final PaymentTokenService paymentTokenService;

  @Override
  public void create(TokenCreateV1.Request request, StreamObserver<TokenCreateV1.Response> responseObserver)
  {
    TokenCreateV1.Response response;

    try
    {
      CommonResponse<PaymentTokenInfo> token = paymentTokenService.createToken(
        request.getDi(),
        request.getMid(),
        request.getPaymentId(),
        request.getPaymentMethodId(),
        request.getExpectAmount()
      );

      response = TokenCreateV1.Response.newBuilder()
        .setToken(token.getData().getToken())
        .setApprovalUrl(token.getData().getApprovalUrl())
        .setCode(token.getCode())
        .setMessage(token.getMessage())
        .build();
    }
    catch (KnownException e)
    {
      response = TokenCreateV1.Response.newBuilder()
        .setCode(e.getCode())
        .setMessage(e.getMessage())
        .build();
    }
    catch (Exception e)
    {
      response = TokenCreateV1.Response.newBuilder()
        .setCode(ExceptionEnum.UNKNOWN_ERROR.getCode())
        .setMessage(ExceptionEnum.UNKNOWN_ERROR.getMessage())
        .build();
    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
