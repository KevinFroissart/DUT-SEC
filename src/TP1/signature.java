package TP1;

/*
signe un message contenu dans le programme
java signature

 */

import java.nio.file.*;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.MessageDigest;

//import javax.xml.bind.DatatypeConverter;

import javax.crypto.Cipher;

public class signature {

	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();

		return pair;
	}

	public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(cipherText);

		Cipher decriptCipher = Cipher.getInstance("RSA");
		decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		return new String(decriptCipher.doFinal(bytes), UTF_8);
	}

	public static String sign(String plainText, PrivateKey privateKey) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes(UTF_8));

		byte[] signature = privateSignature.sign();

		return Base64.getEncoder().encodeToString(signature);
	}

	public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes(UTF_8));

		byte[] signatureBytes = Base64.getDecoder().decode(signature);

		return publicSignature.verify(signatureBytes);
	}

	public static byte[] createChecksum(String filename) throws Exception {
		byte[] b = Files.readAllBytes(Paths.get(filename));
		byte[] hash = MessageDigest.getInstance("SHA1").digest(b); //possible aussi avec MD5 au lieu de SHA1 mais moins performant   

		return hash;   
	}


	public static void main(String[] args) throws Exception{

		//Generation d'une paire de clés RSA
		KeyPair pair = generateKeyPair();

		//Message secret
		String message = args[1].toString();	

		//chiffrement du message secret
		String cipherText = encrypt(message, pair.getPublic());
		System.out.println("Message chiffré:  "+cipherText);
		System.out.println();

		//déchiffrement du message secret
		String decipheredMessage = decrypt(cipherText, pair.getPrivate());
		System.out.println("Message déchiffré:  "+decipheredMessage);
		System.out.println();

		//calcul et affichage du digest d'un fichier
		String Dig =getMD5Checksum("signature.class");
		System.out.println("Digest du fichier signature.class:  "+Dig);
		System.out.println();

		if(args[0].matches("S")) {
			//signature du digest avec la clé privée
			String signature = sign(Dig, pair.getPrivate());
			System.out.println("Digest signé:  "+signature);
			System.out.println();
		}

		if(args[0].matches("V")) {
			//Verification de la signature avec le bon fichier
			boolean isCorrect = verify(getMD5Checksum("signature.class"), args[2], pair.getPublic());
			System.out.println("Test verification avec le fichier signature.class");	
			System.out.println("Signature est elle correcte ? : " + isCorrect);
			System.out.println();

			//Verification de la signature avec un mauvais fichier
			boolean isWrong = verify(getMD5Checksum("signature.java"), args[2], pair.getPublic());

			System.out.println("Test verification avec le fichier signature.java");
			System.out.println("Signature est elle correcte ? : " + isWrong);
		}
	}
}