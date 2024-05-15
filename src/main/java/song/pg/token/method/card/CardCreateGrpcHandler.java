package song.pg.token.method.card;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import song.pg.payment.method.card.create.v1.proto.MethodCardCreateV1;
import song.pg.payment.method.card.create.v1.proto.PaymentMethodCardCreateServiceGrpc;
import song.pg.token.method.PaymentMethodService;
import song.pg.token.models.common.CommonResponse;
import song.pg.token.models.payment.method.ResponsePaymentMethod;
import song.pg.token.models.payment.method.card.RequestPaymentMethodCardRegister;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.KnownException;

@GrpcService
@RequiredArgsConstructor
public class CardCreateGrpcHandler extends PaymentMethodCardCreateServiceGrpc.PaymentMethodCardCreateServiceImplBase
{
  private final PaymentMethodService paymentMethodService;

  @Override
  public void createCardInfo(MethodCardCreateV1.Request request, StreamObserver<MethodCardCreateV1.Response> responseObserver)
  {
    MethodCardCreateV1.Response response;

    try
    {
      CommonResponse<ResponsePaymentMethod> registeredCardInfo = paymentMethodService.registerCardInfo(
        request.getDi(),
        request.getMid(),
        new RequestPaymentMethodCardRegister(request.getCardInfo())
      );

      response = MethodCardCreateV1.Response.newBuilder()
        .setId(registeredCardInfo.getData().getId())
        .setNickName(registeredCardInfo.getData().getNickName())
        .setCode(registeredCardInfo.getCode())
        .setMessage(registeredCardInfo.getMessage())
        .build();
    }
    catch (KnownException e)
    {
      response = MethodCardCreateV1.Response.newBuilder()
        .setCode(e.getCode())
        .setMessage(e.getMessage())
        .build();
    }
    catch (Exception e)
    {
      response = MethodCardCreateV1.Response.newBuilder()
        .setCode(ExceptionEnum.UNKNOWN_ERROR.getCode())
        .setMessage(ExceptionEnum.UNKNOWN_ERROR.getMessage())
        .build();
    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
