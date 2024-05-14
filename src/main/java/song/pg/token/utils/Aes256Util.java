package song.pg.token.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class Aes256Util {
  public static String algorithms = "AES/CBC/PKCS5Padding";
  private final static String AESIv = "0123456789abcdef"; //16byte

  // AES 암호화
  public static String encrypt(final String plain, final String key){
    try {
      Cipher cipher = Cipher.getInstance(algorithms);

      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

      IvParameterSpec ivParamSpec = new IvParameterSpec(AESIv.getBytes());

      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

      byte[] encrypted = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));

      return Base64.getEncoder().encodeToString(encrypted);
    }

    catch (Exception e) {
      log.error("암호화 중 오류 발생하였습니다. {}", e.getMessage());
      throw new KnownException(ExceptionEnum.ENCRYPT_ERROR);
    }
  }

  public static String decrypt(final String encrypted, final String key){
    try {
      Cipher cipher = Cipher.getInstance(algorithms);

      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

      IvParameterSpec ivParamSpec = new IvParameterSpec(AESIv.getBytes());

      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
      byte[] decrypted = cipher.doFinal(decodedBytes);

      return new String(decrypted, StandardCharsets.UTF_8);
    }

    catch (Exception e) {
      log.error("복호화 중 오류 발생하였습니다. {}", e.getMessage());
      throw new KnownException(ExceptionEnum.DECRYPT_ERROR);
    }
  }
}
