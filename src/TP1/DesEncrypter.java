package TP1;

/*
chiffrement
Java desEnc motDePasse TexteaChiffrerEnClair

sortie: leTexteChiffré

Java desDec motdepasse leTextechiffré
le texteChiffré est copié collé lors de la phase de chiffrement

attention à la longueur du mot de passe (8 car pour DES, 16 pour AEs, 24 pour 3DES, on utilise DESede, la version SUN de 3DES
*/

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

//Classe avec les fonction de chiffrement et de déchiffrement
class DesEncrypter {
  Cipher ecipher;

  Cipher dcipher;

  DesEncrypter(SecretKey key) throws Exception {
    ecipher = Cipher.getInstance("DESede");
    dcipher = Cipher.getInstance("DESede");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }
//Chiffrement
  public String encrypt(String str) throws Exception {
  
    // Encode une chaîne en octets utf-8
    byte[] utf8 = str.getBytes("UTF8");

    // Chiffrement
    byte[] enc = ecipher.doFinal(utf8);

    // Encode les octets en base64 pour obtenir un string
	Base64.Encoder encoder = Base64.getEncoder();
	String n64=encoder.encodeToString(enc);
	
	return n64;
  }
public String decrypt(String str) throws Exception {
  // Decode base64 pour obtenir des bytes
	byte[] dec = Base64.getDecoder().decode(str);
	byte[] utf8 = dcipher.doFinal(dec);

    // Decode utf-8
    return new String(utf8, "UTF8");
  }

}
