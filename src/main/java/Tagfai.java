import com.icafe4j.image.jpeg.JPEGTweaker;
import com.icafe4j.image.meta.iptc.IPTCDataSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class Tagfai {

	public static void main(String[] args) throws Exception {

		File image = new File(args[0]);

		// create collection to store all keywords found in an image
		Set<IPTCDataSet> keywords = new HashSet<>();

		IPTCDataSet keyword = new IPTCDataSet(25, "kurtlee");
		keywords.add(keyword);


		// create new image  holding tags information
		FileInputStream is = new FileInputStream(image);
		FileOutputStream os = new FileOutputStream("taggified.jpg");
		JPEGTweaker.insertIPTC(is, os, keywords, false);

	}
}