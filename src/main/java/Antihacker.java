import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Date: 23/03/15.
 * Time: 17:58
 */
public class Antihacker  implements CommandLineRunner {

	public void run(String... args) {
		String username = args[0];
		String password = args[1];
		String directory = args[2];
		File dir = new File(directory);
		File[] files = dir.listFiles();

		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}
		});
		String result = "";
		if(files!=null && files[files.length-1]!=null) {
			String thisLine;
			//Open the file for reading
			try {
				BufferedReader br = new BufferedReader(new FileReader(files[files.length - 1]));
				while ((thisLine = br.readLine()) != null) { // while loop begins here
					if(thisLine.contains("php")) {
						result += thisLine;
					}
				} // end while 
			} // end try
			catch (IOException e) {
				System.err.println("Error: " + e);
			}
			List<String> adjuntos = new ArrayList<String>();
			adjuntos.add(files[files.length-1].getAbsolutePath()) ;
			if(result.equals("")) {
				result = "No se han encontrado archivos php.";
			} else {
				result = "Se han encontrado los siguientes archivos php:\n"+result;
			}
			new SendMailSSL().sendGMail(username, password, "adria@seguim.com","adriafp@gmail.com","[ANTIHACKER] Report of: " + new Date(),result, adjuntos);
			
			//TODO Clean old log files
			//TODO Backup to AWS S3
		}
	}
	
}
