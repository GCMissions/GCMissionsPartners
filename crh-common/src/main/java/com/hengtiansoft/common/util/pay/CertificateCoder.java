package com.hengtiansoft.common.util.pay;


import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@author:jianghm
 *@date:2016年6月21日 上午10:33:13
 *@description:
 */
public class CertificateCoder {
    private static final Logger log= LoggerFactory.getLogger(CertificateCoder.class);
	public static final String CERT_TYPE = "X.509";

	/**
	 * 
	 * @param certificatePath
	 * @return Certificate 证书
	 * @throws Exception
	 */
	private static Certificate getCertificate(String certificatePath) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance(CERT_TYPE);
		FileInputStream in = new FileInputStream(certificatePath);
		Certificate certificate = factory.generateCertificate(in);
		in.close();
		return certificate;

	}

	/**
	 * 签名
	 * 
	 * @param sign
	 * @param strPfx
	 * @param strPassword
	 * @return
	 * @throws Exception
	 */
	public static String sign(String sign, String strPfx, String strPassword) throws Exception {
		// 获取证书
		X509Certificate x509 = (X509Certificate) getCertformPfx(strPfx, strPassword);
		// 构建签名,由证书指定签名算法
		Signature sa = Signature.getInstance(x509.getSigAlgName());
		// 获取私匙
		PrivateKey privateKey = getPvkformPfx(strPfx, strPassword);
		sa.initSign(privateKey);
		sa.update(sign.getBytes("GBK"));
		return Base64.encodeBase64String(sa.sign());
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 * @param sign
	 * @param cerPath
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String data, String sign, String cerPath) throws Exception {
		X509Certificate x509 = (X509Certificate) getCertificate(cerPath);
		Signature sa = Signature.getInstance(x509.getSigAlgName());
		sa.initVerify(x509);
		sa.update(data.getBytes("GBK"));
		return sa.verify(Base64.decodeBase64(sign.getBytes("GBK")));
	}
	
	/**
	 * 验证签名
	 * 
	 * @param data
	 * @param sign
	 * @param x509
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String data, String sign, X509Certificate x509) throws Exception {
		Signature sa = Signature.getInstance(x509.getSigAlgName());
		sa.initVerify(x509);
		sa.update(data.getBytes("GBK"));
		return sa.verify(Base64.decodeBase64(sign.getBytes("GBK")));
	}
	
	// 转换成十六进制字符串
	public static String Byte2String(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static byte[] StringToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = b.length - 1; i > -1; i--) {
			b[i] = Integer.valueOf(temp & 0xff).byteValue();// 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}
	
	private static PrivateKey getPvkformPfx(String strPfx, String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strPfx);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
				//System.out.println("alias=[" + keyAlias + "]");
			}
			// Now once we know the alias, we could get the keys.
			return (PrivateKey) ks.getKey(keyAlias, nPassword);
		} catch (Exception e) {
		    log.error("msg",e);
		}
		return null;
	}
	
	private static Certificate getCertformPfx(String strPfx, String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strPfx);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
			}
			// Now once we know the alias, we could get the keys.
			return ks.getCertificate(keyAlias);
		} catch (Exception e) {
		    log.error("msg",e);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		// 签名测试
		System.out.println(CertificateCoder.sign("签名串", "pfx证书路径", "pfx证书密码"));
	}
}
