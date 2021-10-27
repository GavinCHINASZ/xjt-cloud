package com.xjt.cloud.sys.core.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.dao.sys.UserDao;
import com.xjt.cloud.sys.core.entity.JwtEntity;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.service.service.JwtService;
import com.xjt.cloud.sys.core.service.service.LoginService;
import com.xjt.cloud.sys.core.service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt ServiceImpl
 *
 * @author huanggc
 * @date 2020/11/23
 */
@Service
public class JwtServiceImpl extends AbstractService implements JwtService {
    private static String public_Key = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnzyis1ZjfNB0bBgKFMSv\n" +
            "vkTtwlvBsaJq7S5wA+kzeVOVpVWwkWdVha4s38XM/pa/yr47av7+z3VTmvDRyAHc\n" +
            "aT92whREFpLv9cj5lTeJSibyr/Mrm/YtjCZVWgaOYIhwrXwKLqPr/11inWsAkfIy\n" +
            "tvHWTxZYEcXLgAXFuUuaS3uF9gEiNQwzGTU1v0FqkqTBr4B8nW3HCN47XUu0t8Y0\n" +
            "e+lf4s4OxQawWD79J9/5d3Ry0vbV3Am1FtGJiJvOwRsIfVChDpYStTcHTCMqtvWb\n" +
            "V6L11BWkpzGXSW4Hv43qa+GSYOD2QU68Mb59oSk2OB+BtOLpJofmbGEGgvmwyCI9\n" +
            "MwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    private static String jwt_token = "eyJraWQiOiIxMDAiLCJhbGciOiJSUzI1NiJ9" +
            ".eyJpc3MiOiJ1cm46Y29tOm5ldHdvcmtudDpvYXV0aDI6djEiLCJhdWQiOiJ1cm46Y29tLm5ldHdvcmtudCIsImV4cCI6MTYwNjM4MDg5NSwianRpIjoiSzZrcDJQMnN2RDhVbVNOTmdqT3ZXdyIsInZlcnNpb24iOiIxLjAiLCJ1c2VyIjoiY2hlbm1pbmciLCJ1c2VyX3R5cGUiOm51bGwsImNsaWVudF9pZCI6Ijc1OTk5NjI4LWY2NzEtNDg3NC05YTJmLTE0OWIzYjFkMDZjMiIsInNjb3BlIjpbImFjdC5yIiwiYWN0LnciXX0" +
            ".Qbj1jlODOgz741iAJNFyPR5oFqqFzB7Og2sbn-DPED357vLdejBiGla87gwfHY_qdEfLvcuYPoy5rS3k3Pu7wOXkfBTmSCOZ4bqrRg30SXEjLJRMeaQRAWBkbPQ4zhb7Nu0onVsTyyV1xnCPBAhqgqi60eWosD1ILa4kZaj0iEKmNkmKtQCG36qY4dprFJDrmiaK1WxLb4HF3H4EUMvRM0b6PUBmg81M9WdqYdxiQHlZfczKXssym0HxBHEYGigAeABVg-WbjG_i-svaVJefoJogfjhra4oDzDDtg0mElpwl4nf7IJqlRF7VBFAE7wWUGEU9bqDB9BVrm9_BQbrMeA";

    private static final String KEY_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";
    private static final int KEY_SIZE = 2048;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginService loginService;

    public static void main(String[] args) throws Exception {
        //String sheQuYunJwtAuthorizePublicKey = ConstantsClient.SHE_QU_YUN_JWT_AUTHORIZE_PUBLIC_KEY;
        //PublicKey publicKey = getPublicKey(ConstantsClient.SHE_QU_YUN_JWT_AUTHORIZE_PUBLIC_KEY);
        PublicKey publicKey = getPublicKey(public_Key);
        Claims claims = null;
        try {
            // exp:时间过期后,claims为null
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt_token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (claims != null){
            System.out.println(claims.get("user"));
        }
    }

    /**
     * 获取PublicKey对象
     *
     * @param publicKeyStr public_Key
     * @return PublicKey
     */
    private static PublicKey getPublicKey(String publicKeyStr) {
        PublicKey publicKey = null;
        try {
            String pem = publicKeyStr.replaceAll("\\-*BEGIN PUBLIC KEY\\-*", "").replaceAll("\\-*END PUBLIC KEY\\-*", "").trim();
            java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pem));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = keyFactory.generatePublic(pubKeySpec);
        }catch (Exception e){
            SysLog.debug("----------->getPublicKey失败");
        }
        return publicKey;
    }

    /**
     * 功能描述: JWT验证 接口
     *
     * @param jwtEntity JwtEntity
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/23
     */
    @Override
    public Data authorizeCallback(JwtEntity jwtEntity, String cloudType, HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String token = jwtEntity.getJwt_authorization();
        SysLog.debug("----------->authorizeCallback--jwt_authorization=" + token);
        //String userString = request.getHeader("user");
        String sheQuYunJwtAuthorizePublicKey = ConstantsClient.SHE_QU_YUN_JWT_AUTHORIZE_PUBLIC_KEY;
        PublicKey publicKey = getPublicKey(sheQuYunJwtAuthorizePublicKey);
        Claims claims;
        try {
            // exp:时间过期后,claims为null
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            SysLog.debug("----------->authorizeCallback--claims失败");
            return Data.isFail();
        }
        if (claims == null){
            // 失败	验证失败，跳转到登录失败提示页???
            SysLog.debug("----------->authorizeCallback失败");
            return Data.isFail();
        }

        // 成功	验证成功后，直接跳转到登录成功页
        // 用户名
        String userName = claims.get("user").toString();
        SysLog.debug("----------->authorizeCallback--userName=" + userName);
        User user = userService.isUserExistByPhone(null, userName,null, true);
        // 判断是否存在用户
        if (user == null) {
            return Data.isFail();
        }else {
            return loginService.userLogin(user.getLoginName(), user.getPassword(), cloudType);
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token token
     * @return boolean
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(ConstantsClient.SHE_QU_YUN_JWT_AUTHORIZE_CALLBACK_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 未验证通过会抛出异常
            verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * 生成密钥对
     *
     * @return Map<String, byte[]>
     */
    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥
     *
     * @param keyBytes publicKeyString
     * @return PublicKey
     */
    private static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥
     *
     * @param keyBytes privateKeyString
     * @return PrivateKey
     */
    private static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
