import common.utils.QQUtils;
import common.utils.WeChatUtils;
import common.utils.WeiboUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSASignature;
import sun.security.util.ObjectIdentifier;

import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by xiexw on 2017/8/31.
 */
public class test {

    public static void main(String[] args) throws Exception {
//        System.out.println(WeChatUtils.CODE_URL);
//        System.out.println(getRandomSalt(8));

        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        random.setSeed("1234".getBytes());
        keygen.initialize(1024, random);
        KeyPair keyPair = keygen.genKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println(Arrays.toString(publicKey.getEncoded()));
        System.out.println(Arrays.toString(privateKey.getEncoded()));

//        byte[] bytes = "111".getBytes();
//        int[] ints = new int[]{1, 1, 1};
//        String md5c = Md5Crypt.md5Crypt(bytes, "$1$1234");
//
//        System.out.println(md5c);
//        System.out.println(Arrays.toString(RSASignature.MD5withRSA.encodeSignature(ObjectIdentifier.newInternal(ints), bytes)));
//        System.out.println(Md5Crypt.apr1Crypt("111".getBytes()));
    }

}
