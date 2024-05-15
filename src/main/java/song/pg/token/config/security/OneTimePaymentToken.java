package song.pg.token.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import song.pg.token.models.payment.token.PaymentTokenVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
public class OneTimePaymentToken implements UserDetails, Serializable
{
  private static final long serialVersionUID = -2515985495105983985L;

  private PaymentTokenVo paymentTokenVo;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return List.of(new SimpleGrantedAuthority("OTP_PAYMENT_TOKEN"));
  }

  public OneTimePaymentToken(final PaymentTokenVo paymentTokenVo)
  {
    this.paymentTokenVo = paymentTokenVo;
  }

  @Override
  public String getPassword()
  {
    return paymentTokenVo.getPaymentMethodId();
  }

  @Override
  public String getUsername()
  {
    return paymentTokenVo.getPaymentId();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }

}
