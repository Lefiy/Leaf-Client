package com.leafclient.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;

public class SecureReader {
	
	private String KEY;
	
	private final String PATH = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + "datakey" + File.separator;
	
	private byte[] salt = {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};
		
	private String getKey() {
		try {
			File file = new File(PATH + "value");
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String key = reader.readLine(); reader.close();
			return key;
		} catch (IOException e) {}
		return null;
	}
		
	public void writer(String value) {
		if(KEY == null) return;
		try {
			
			byte[] encrypto = encrypto(value);
			
			int files = new File(PATH).listFiles().length;
			File file = new File(PATH + files); file.createNewFile();
			FileOutputStream fileout = new FileOutputStream(file);
			fileout.write(encrypto); fileout.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public String reader(final String key, final int index) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		if(!encode.matches(key, getKey())) return null; KEY = key;
		try {
			
			byte[] info = null;
			
			int files = new File(PATH).listFiles().length;
			
			for(int i = 1; i < files; i++) {
				
				String path = PATH + String.valueOf(i);
				File file = new File(path);
				FileInputStream input = new FileInputStream(file);
				info = new byte[(int)file.length()];
		        input.read(info); input.close();
		        
		        String decrypto = decrypto(info);
		        
		        Client.getInstance().getKeys().add(decrypto);
		        
		        if(i == index) {
		        	Client.getInstance().tokenSession(decrypto);
		        }
			}
			
	        return "";
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String decrypto(byte[] cryptoText) throws GeneralSecurityException, IOException {
		
		PBEParameterSpec pbeParamSpecDec = new PBEParameterSpec(salt, 2048);

        char[] pswd = KEY.toCharArray();
        PBEKeySpec pbeKeySpecDec = new PBEKeySpec(pswd);
        Arrays.fill(pswd, (char)0x00);
        SecretKeyFactory keyFacDec = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey pbeKeyDec = keyFacDec.generateSecret(pbeKeySpecDec);

        Cipher cDec = Cipher.getInstance("PBEWithMD5AndDES");
        cDec.init(Cipher.DECRYPT_MODE, pbeKeyDec, pbeParamSpecDec);

        byte[] output = cDec.doFinal(cryptoText);
        
        return new String(output);
		
    }
	
	private byte[] encrypto(String plainText) throws GeneralSecurityException, IOException {
		
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 2048);

        char[] password = KEY.toCharArray();
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        Arrays.fill(password, (char)0x00);
        SecretKeyFactory  keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
        
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

        byte[] cleartext = plainText.getBytes();

        byte[] ciphertext = pbeCipher.doFinal(cleartext);
        
        return ciphertext;
    }
}