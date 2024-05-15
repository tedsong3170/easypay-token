package song.pg.token.document;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import song.pg.token.config.security.OneTimePaymentToken;
import song.pg.token.models.payment.token.PaymentTokenEntity;
import song.pg.token.models.payment.token.PaymentTokenVo;
import song.pg.token.repository.PaymentTokenRepository;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TokenDocsTest
{
  private MockMvc mockMvc;

  @Autowired
  private PaymentTokenRepository paymentTokenRepository;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation))
      .build();
  }

  @Test
  @DisplayName("토큰 검증 및 카드정보 요청")
  void registerCardInfo() throws Exception
  {
    PaymentTokenEntity tokenEntity = paymentTokenRepository.findByPaymentId("a9d04e4880554d6ea80e218ec8a256c1");
    UserDetails user = new OneTimePaymentToken(new PaymentTokenVo(tokenEntity));

    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user, user.getAuthorities());
    SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    this.mockMvc.perform(
        post("/api/token/verify/v1")
          .contentType(MediaType.APPLICATION_JSON)
          .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJwYXltZW50TWV0aG9kSWQiOiJhNzg1NTUxYjljMzk0OTgyOWZjZTI0OTJjZGExY2E1OSIsInBheW1lbnRJZCI6ImE5ZDA0ZTQ4ODA1NTRkNmVhODBlMjE4ZWM4YTI1NmMxIiwiZXhwZWN0QW1vdW50IjoxMDAwMDAwLCJpYXQiOjE3MTU3NzQ0MzYsImV4cCI6MTcxNTc3NDczNn0.xzFmr6cnxHecvj_DEKSbUQmEDrZOodd2AHB_PueMkzs")
          .content(
            """
            """
          )
          .with(user(user))
          .with(authentication(authentication))
          .with(securityContext(securityContext))
      )
      .andExpect(status().isOk())
      .andDo(
        document("payment/token/verify",
          resource(
            ResourceSnippetParameters.builder()
              .description("토큰 검증 및 카드정보 요청")
              .requestHeaders(
                headerWithName("Authorization").description("Bearer 결제토큰")
              )
              .responseFields(
                fieldWithPath("code").description("응답코드").type(STRING),
                fieldWithPath("message").description("응답메세지").type(STRING),
                fieldWithPath("data").description("응답결과").type(OBJECT),
                fieldWithPath("data.cardNumber1").description("카드 번호 첫번째 4자리").type(STRING),
                fieldWithPath("data.cardNumber2").description("카드 번호 두번째 4자리").type(STRING),
                fieldWithPath("data.cardNumber3").description("카드 번호 세번째 4자리(암호화)").type(STRING),
                fieldWithPath("data.cardNumber4").description("카드 번호 네번째 4자리(암호화)").type(STRING),
                fieldWithPath("data.expireYear").description("카드 만료 연도").type(NUMBER),
                fieldWithPath("data.expireMonth").description("카드 만료 월").type(NUMBER),
                fieldWithPath("data.cvc").description("카드 CVC").type(NUMBER),
                fieldWithPath("data.password").description("비밀번호 앞 2글자").type(NUMBER),
                fieldWithPath("data.cardHolderName").description("카드 소유주 이름").type(STRING)
              )
              .build()
          )
        )
      );
  }
}
