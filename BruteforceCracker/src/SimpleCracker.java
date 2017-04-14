import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;


public class SimpleCracker {
	public static void main(String [] a){
		Scanner inFile = null;
		Scanner common = null;
		String verifier=null;
		List<String> temp = new ArrayList<String>();
		List<String> commons= new ArrayList<String>();
		List<String>finalconcat = new ArrayList<String>();
		List<String> crypt = new ArrayList<String>();
		List<String> userpass = new ArrayList<String>();

		try {
			common = new Scanner(new File("common-passwords.txt"));
			while(common.hasNext()){
				commons.add(common.next());
			}
			inFile = new Scanner(new File("shadow-simple")).useDelimiter("\n");
			while(inFile.hasNext()){
				temp.add(inFile.next());	
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
			    for (int i=0;i<temp.size();i++) {
			    	for(int j=0;j<commons.size();j++){
			    	String saltpass =  temp.get(i).substring(6,14)+commons.get(j);			    	
			      finalconcat.add(saltpass);
			    	}
			      crypt.add(temp.get(i).substring(15,47));
			    }
			    try {
			List<String> converted = new ArrayList<String>();
			MessageDigest md;
			
				md = MessageDigest.getInstance("MD5");
			
			byte[] digest = null;
			byte[] digest1 = null;
			for(int i1=0;i1<finalconcat.size();i1++){
					md.update(finalconcat.get(i1).getBytes());
				digest = md.digest();
				converted.add(toHex(digest));
				}
			
			
					for(int check=0;check<converted.size();check++)
					{
						for(int cryptcheck=0;cryptcheck<crypt.size();cryptcheck++){
						if(converted.get(check).contains(crypt.get(cryptcheck)))
							{
							for(int finalcon=0;finalcon<finalconcat.size();finalcon++){
								md.update(finalconcat.get(finalcon).getBytes());
								digest1 = md.digest();
								verifier=toHex(digest1);			
									if(verifier.equals(converted.get(check))){
									userpass.add(finalconcat.get(finalcon).substring(0, 8));
									for(int tempsearch=0;tempsearch<temp.size();tempsearch++)

										if((temp.get(tempsearch).substring(6,14)).contains(finalconcat.get(finalcon).substring(0,8))){
											System.out.println(temp.get(tempsearch).substring(0,5)+":"+finalconcat.get(finalcon).substring(8));
										}
									}
							}
							}
						}
					}} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
		}
	
		public static String toHex(byte[] bytes)
	    {
	        BigInteger bi = new BigInteger(1, bytes);
	        return String.format("%0" + (bytes.length << 1) + "X", bi);
	    }

	}
	  
