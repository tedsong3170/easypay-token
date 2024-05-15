package song.pg.token.models.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_bin_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardBinInfoEntity
{
  // BIN번호
  @Id
  @Column(name="bin", nullable = false)
  private String bin;

  // 발급사
  @Column(name="company", nullable = false)
  private String company;

  // 카드구분
  @Column(name="owner_type", nullable = false)
  private String ownerType;

  // 브랜드
  @Column(name="brand", nullable = false)
  private String brand;

  // 승인요청 URL
  @Column(name="approval_url", nullable = false)
  private String approvalUrl;
}
