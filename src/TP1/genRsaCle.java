package TP1;

/* Generation d'une paire de clé RSA en binaire et en base 64

utilisation
javac genRsaCle.java
java genRsaCle nomDeLaCle     

genère dans un fichier, clè privée et publique au format base64 et binaire

 */

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class genRsaCle {

	public static void main(String [] args) {
		//throws Exception

		try{ 
			// generation des 2 clés (privée, publique)
			KeyPair keyPair = buildKeyPair();
			PublicKey pubKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();

			//stockage des clès en binaire
			String outFile = args[0];
			FileOutputStream out = new FileOutputStream(outFile + ".pub");
			out.write(pubKey.getEncoded());
			out.close();

			out = new FileOutputStream(outFile + ".priv");
			out.write(privateKey.getEncoded());
			out.close();

			// version en base64 des clés (en ASCII)
			Base64.Encoder encoder = Base64.getEncoder();

			String outFileb64 = "b64RsaKey";
			Writer outb64 = new FileWriter(outFileb64 + ".priv");
			outb64.write("-----BEGIN RSA PRIVATE KEY-----\n");
			outb64.write(encoder.encodeToString(privateKey.getEncoded()));
			outb64.write("\n-----END RSA PRIVATE KEY-----\n");
			outb64.close();

			outb64 = new FileWriter(outFileb64 + ".pub");
			outb64.write("-----BEGIN RSA PUBLIC KEY-----\n");
			outb64.write(encoder.encodeToString(pubKey.getEncoded()));
			outb64.write("\n-----END RSA PUBLIC KEY-----\n");
			outb64.close();

			// identification du format de stockage
			System.out.println("Private key format: " + privateKey.getFormat());
			// Affiche "Private key format: PKCS#8"

			System.out.println("Public key format: " + pubKey.getFormat());
			// Affiche "Public key format: X.509"
		}catch (Exception e) {System.out.println(e);} 

	}

	public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
		final int keySize = 2048;
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keySize);    

		return keyPairGenerator.genKeyPair();
	}
}