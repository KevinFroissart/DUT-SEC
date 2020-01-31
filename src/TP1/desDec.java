package TP1;

/*
chiffrement
Java desEnc motDePasse TexteaChiffrerEnClair

sortie: leTexteChiffré

Java desDec motdepasse leTextechiffré
le texteChiffré est copié collé lors de la phase de chiffrement

attention à la longueur du mot de passe (8 car pour DES, 16 pour AEs, 24 pour 3DES), on utilise DESede, la version SUN de 3DES
*/

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class desDec {
  public static void main(String[] argv) throws Exception {
//AES: clé de 16 octets, 8 pour DES 24 pour 3DES (DESede)
	String mykey =argv[0];
        SecretKey key = new SecretKeySpec(mykey.getBytes(), "DESede"); 
	System.out.println(key);
    	DesEncrypter encrypter = new DesEncrypter(key);
  	String decrypted = encrypter.decrypt(argv[1]);
	System.out.println(decrypted);
  }
}
