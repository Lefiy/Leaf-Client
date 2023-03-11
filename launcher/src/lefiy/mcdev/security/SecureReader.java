package lefiy.mcdev.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lefiy.mcdev.utils.PathUtils;

public class SecureReader {
	
	public void setKey(final String key) {
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		try {
			File file = new File(util.getMinecraftData() + File.separator + "datakey" + File.separator + "value");
			FileWriter writer = new FileWriter(file, false);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encode = encoder.encode(key);
			writer.write(encode);
			writer.close();
		}catch (IOException e) {e.printStackTrace();}
	}
	
	public String getKey() {
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		try {
			File file = new File(util.getMinecraftData() + File.separator + "datakey" + File.separator + "value");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String key = reader.readLine(); reader.close();
			return key;
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}