package song.pg.token.method;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import song.pg.payment.method.findAll.v1.proto.MethodFindAllV1;
import song.pg.payment.method.findAll.v1.proto.PaymentMethodFindAllServiceGrpc;

@GrpcService
public class PaymentMethodService extends PaymentMethodFindAllServiceGrpc.PaymentMethodFindAllServiceImplBase
{
  @Override
  public void findAllPaymentMethod(MethodFindAllV1.Request request, StreamObserver<MethodFindAllV1.Response> responseObserver)
  {
    MethodFindAllV1.Response response = MethodFindAllV1.Response.newBuilder()
      .addPaymentMethod(MethodFindAllV1.PaymentMethod.newBuilder()
        .setId("1234")
        .setMethod("CARD")
        .setNickName("첫번째카드")
        .build()
      )
      .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
