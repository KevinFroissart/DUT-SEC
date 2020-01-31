package TP1;

/*
déchiffre avec la clé privée
java dechiffreRSA fichierClePrive fichierChiffre

*/

import java.nio.file.*;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.*;
import javax.crypto.Cipher;

public class dechiffreRSA {
    
    public static void main(String [] args) throws Exception {


	// Lecture des octets de la clé privée depuis le fichier
	byte[] bytesPriv = Files.readAllBytes(Paths.get(args[0]));

	// regenere la clé privée en mémoire
	PKCS8EncodedKeySpec ksPriv = new PKCS8EncodedKeySpec(bytesPriv);
	KeyFactory kfPriv = KeyFactory.getInstance("RSA");
	PrivateKey privateKey = kfPriv.generatePrivate(ksPriv);

/*      morceau de code utile

	// Lecture des octets de la clé publique depuis le fichier
	byte[] bytesPub = Files.readAllBytes(Paths.get(args[0]));
 
	// regenere la clé publique en mémoire
	X509EncodedKeySpec ksPub = new X509EncodedKeySpec(bytesPub);
	KeyFactory kfPub = KeyFactory.getInstance("RSA");
	PublicKey pubKey = kfPub.generatePublic(ksPub);
*/
	byte[] encrypted = Files.readAllBytes(Paths.get(args[1]));
        	     
	// dechiffre le message avec la clé privée   	
        byte[] secret = decrypt(privateKey, encrypted);                                 
        System.out.println(new String(secret));     // Message secret
    }

public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(encrypted);
	}
}
