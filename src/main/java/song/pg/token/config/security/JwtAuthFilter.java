package song.pg.token.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import song.pg.token.models.payment.token.PaymentTokenEntity;
import song.pg.token.models.payment.token.PaymentTokenVo;
import song.pg.token.repository.PaymentTokenRepository;
import song.pg.token.utils.ExceptionEnum;
import song.pg.token.utils.JwtUtil;
import song.pg.token.utils.KnownException;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
  private final JwtUtil jwtUtil;
  private static final String AUTH_HEADER = "Authorization";
  private static final String BEARER = "Bearer ";
  private final PaymentTokenRepository paymentTokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
  {
    log.debug("JWT 인증 필터 시작");
    final String authHeader = request.getHeader(AUTH_HEADER);

    if (authHeader != null && authHeader.startsWith(BEARER))
    {
      final String token = authHeader.substring(BEARER.length());
      final Claims claims = jwtUtil.getTokenClaims(token);

      final String paymentId = claims.get("paymentId", String.class);
      final String paymentMethodId = claims.get("paymentMethodId", String.class);
      final Long expectAmount = claims.get("expectAmount", Long.class);

      Optional<PaymentTokenEntity> entity = paymentTokenRepository.findById(token);

      if (entity.isEmpty())
      {
        throw new KnownException(ExceptionEnum.DOSE_NOT_EXIST_TOKEN);
      }

      if (!entity.get().getPaymentId().equals(paymentId)
        || !entity.get().getPaymentMethodId().equals(paymentMethodId)
        || entity.get().getExpectAmount().longValue() != expectAmount
      )
      {
        throw new KnownException(ExceptionEnum.CHANGE_DETECTED_TOKEN);
      }

      UserDetails userDetails = new OneTimePaymentToken(new PaymentTokenVo(entity.get()));

      try
      {
        SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails,
            userDetails.getAuthorities()
          )
        );
      }
      catch (KnownException e)
      {
        log.warn(e.getMessage());
        filterChain.doFilter(request, response);
      }
    }

    filterChain.doFilter(request, response);
  }
}
