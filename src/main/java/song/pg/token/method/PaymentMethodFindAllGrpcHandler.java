package song.pg.token.method;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import song.pg.payment.method.findAll.v1.proto.MethodFindAllV1;
import song.pg.payment.method.findAll.v1.proto.PaymentMethodFindAllServiceGrpc;
import song.pg.token.models.payment.method.ResponsePaymentMethod;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PaymentMethodFindAllGrpcHandler extends PaymentMethodFindAllServiceGrpc.PaymentMethodFindAllServiceImplBase
{
  private final PaymentMethodService paymentMethodService;

  @Override
  public void findAllPaymentMethod(MethodFindAllV1.Request request, StreamObserver<MethodFindAllV1.Response> responseObserver)
  {
    List<ResponsePaymentMethod> methodList = paymentMethodService.getPaymentMethods(request.getDi());

    MethodFindAllV1.Response response = MethodFindAllV1.Response.newBuilder()
      .addAllPaymentMethod(methodList.stream()
        .map(method -> MethodFindAllV1.PaymentMethod.newBuilder()
          .setId(method.getId())
          .setMethod(method.getMethod().toString())
          .setNickName(method.getNickName())
          .build()
        )
        .toList()
      )
      .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
