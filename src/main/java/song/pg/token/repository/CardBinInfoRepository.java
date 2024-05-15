package song.pg.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.token.models.common.CardBinInfoEntity;

import java.util.Optional;

@Repository
public interface CardBinInfoRepository extends JpaRepository<CardBinInfoEntity, String>
{
}
