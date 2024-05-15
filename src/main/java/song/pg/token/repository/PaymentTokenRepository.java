package song.pg.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import song.pg.token.models.payment.token.PaymentTokenEntity;

public interface PaymentTokenRepository extends JpaRepository<PaymentTokenEntity, String>
{

  PaymentTokenEntity findByPaymentId(String paymentId);
}
