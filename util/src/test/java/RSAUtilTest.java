import com.zq.util.RSAUtil;
import org.junit.Test;

/**
 * 测试加签验签
 */
public class RSAUtilTest {
    private final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDX4LKchQ28ykdt9cPxa4OLxkTX\n" +
            "C/XAY+PJS35cT5F1T4poVVrTEEOsGRcaQ+JUKrri1Brlvgdg+KqCDLb7CDuSrTkq\n" +
            "Ci+85yVVLi6hoRDa4D/gyqZRXsSEcCP4O6gpf1wVmq7JQQCO34rDAWgaEaUF0DWz\n" +
            "EpWTBy6P+IAndtaVwQIDAQAB";
    static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANfgspyFDbzKR231\n" +
            "w/Frg4vGRNcL9cBj48lLflxPkXVPimhVWtMQQ6wZFxpD4lQquuLUGuW+B2D4qoIM\n" +
            "tvsIO5KtOSoKL7znJVUuLqGhENrgP+DKplFexIRwI/g7qCl/XBWarslBAI7fisMB\n" +
            "aBoRpQXQNbMSlZMHLo/4gCd21pXBAgMBAAECgYEAkaFYfLRZjxlaVHiuBYgFBt7r\n" +
            "A7NixrXcLahLBxT6SIwvD7E9AxN+w3NtnV9cKHFm/6wctQ2bxrQZun+/VNu56zzP\n" +
            "AmLz4u/c4osBqyFlWzaVqLOjSbclRg35B2jpek7SsHq52lBr6jlS+Ja9ekaz5g96\n" +
            "NSbC5uYwr9ryKqki5BECQQDutE/lWtrrJ1NF9joqHcJMbBLUMRpu8GF6X9Gjbitt\n" +
            "jBO/twwJxk6jVzwsOBn+7O6rxDUCaQeMwIRkaTSO06eVAkEA54T6d1forHk2qgkt\n" +
            "P1jjUUvWkkQyWqZ44e79595YpzAXdPwrJqR46og6uC1ZImp4pRxOVrleowF1E375\n" +
            "1e46fQJARF2cLGIzJPpkXTtCOWHAjka9tcxCd9ec84GR9y7MfVmLJnN0nTAQwbNw\n" +
            "ZnHC8EJHsSZG0GPoZ6WTxXZkOSvhaQJAZsaEfZg7xNzmZGNAf9+QwdUdwr6db9p4\n" +
            "zStP8a+lwUo7D4amDTh0KQEiuAIBkKKdPgarzBnsJgaOl4IFnG9t7QJADPZDLG6q\n" +
            "s0V8dIFtP3FT6G7Xk68M5Zwwi5SBQlz5PpXXzC/uGO6kNkKwzZbJK+WF+bhZraBC\n" +
            "DVlqmvVCuqQsBA==";

    /**
     * 输出结果
     * Kj5v+PVHyLNKu0LhrGJ56QJwlOJGrN64SkXSWc53F7+YKGIWg9RjC86+XZRpADTqxHiBu0xfLP5rxhCuNZdEvjLsunvwQNpsIsMkZ7i44Ze5Qu7bxggfWgcxwBhI7Q1HkPwGj8Gnc/i7VJRtvLVoK/PPKte68ChPVU2/j8peRN8=
     */
    @Test
    public void signTets(){
        String text = "bawei";
        String sign = RSAUtil.sign(text, privateKey);
        System.out.println(sign);
        System.out.println(RSAUtil.verify(text, sign, publicKey));
    }
}
