package TP1;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//DESede C  huithuithuithuithuithuit "c'est une message"
//DESede D  huithuithuithuithuithuit IAzAzfCktNpDbvYFzwLUBIafY6/kIE49

public class desEncDec {
  public static void main(String[] argv) throws Exception {

	String mykey=argv[2];
	int limite = 0;
	
	switch(argv[0]) {
		case "DES" : limite = 8; break;
		case "AES" : limite = 16; break;
		case "DESede" : limite = 24; break;
	}
		
	if(argv[2].length() != limite) {
		System.err.println("La taille du mot de passe est incorrecte\nElle doit être égale à " + limite);
		System.exit(-1);
	}
	
    SecretKey key = new SecretKeySpec(mykey.getBytes(), argv[0]); 
	System.out.println(key);
	if(argv[1].matches("C")) {
		DesEncrypter encrypter = new DesEncrypter(key);
		System.out.println(encrypter.encrypt(argv[3]));
	} else if(argv[1].matches("D")) {
		DesEncrypter encrypter = new DesEncrypter(key);
		System.out.println(encrypter.decrypt(argv[3]));
	}
  }
}