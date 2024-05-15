package song.pg.token.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import song.pg.token.config.security.JwtAuthFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig
{

  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain jwtChain(HttpSecurity http) throws Exception
  {
    http
      .securityMatcher("/api/token/**")
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth ->
        auth.anyRequest().authenticated()
      )
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(STATELESS)
      )
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
