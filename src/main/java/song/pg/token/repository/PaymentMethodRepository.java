package song.pg.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.token.models.payment.method.PaymentMethodInfoEntity;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodInfoEntity, String>
{
  List<PaymentMethodInfoEntity> findAllByCustomerDi(String di);
}
