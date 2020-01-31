package TP1;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

//java secureRSA c cle.pub "message1234" mess
//java secureRSA d cle.priv mess

public class secureRSA {
	public static void main(String[] args) throws Exception {

		if(args[0].matches("c")) {
			byte[] bytesPub = Files.readAllBytes(Paths.get(args[1]));
			X509EncodedKeySpec ksPub = new X509EncodedKeySpec(bytesPub);
			KeyFactory kfPub = KeyFactory.getInstance("RSA");
			PublicKey pubKey = kfPub.generatePublic(ksPub);
			byte [] encrypted = encrypt(pubKey, args[2]);          
			System.out.println(new String(encrypted));   
			String outFile = args[3];
			FileOutputStream out = new FileOutputStream(outFile);
			out.write(encrypted);
			out.close();     
		} else if(args[0].matches("d")) {
			byte[] bytesPriv = Files.readAllBytes(Paths.get(args[1]));
			PKCS8EncodedKeySpec ksPriv = new PKCS8EncodedKeySpec(bytesPriv);
			KeyFactory kfPriv = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = kfPriv.generatePrivate(ksPriv);
			byte[] encrypted = Files.readAllBytes(Paths.get(args[2]));
			byte[] secret = decrypt(privateKey, encrypted);                                 
			System.out.println(new String(secret));
		}
	}

	public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");  
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(encrypted);
	}

	public static byte[] encrypt(PublicKey pubKey, String message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");  
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);  
		return cipher.doFinal(message.getBytes());  
	} 
}