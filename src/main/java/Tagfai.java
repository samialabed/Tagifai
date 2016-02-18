import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.icafe4j.image.jpeg.JPEGTweaker;
import com.icafe4j.image.meta.iptc.IPTCDataSet;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tagfai {

	private static ClarifaiClient clarifai;
	private static boolean deleteFlag;

	public static void main(String[] args) throws IOException {
		clarifai = new ClarifaiClient(); // initialise clarifai client using environment variables

		if (args.length < 1) {
			System.err.println("Usage: java -jar Tagifai-1-jar-with-dependencies.jar inputDirectory [-D]");
			return;
		}

		if (args.length == 2 && args[1].equals("-D")) {
			deleteFlag = true;
			System.out.println("Delete original images after process finish!");
		}

		String targetFolder = args[0];
		File[] files = new File(targetFolder).listFiles();
		if (files == null) {
			throw new FileNotFoundException("No files detected in the given path");
		}

		// for each file grab tags using clarifai and update the files with the tags
		for (File file : files) {
			if (ImageIO.read(file) == null)
				continue; // Skip if not an image

			System.out.println("Processing: " + file.getName());
			Collection<IPTCDataSet> keywords = getTags(file);
			insertTags(file, keywords);
			if (deleteFlag)
				file.deleteOnExit(); // if requested mark file after tagging it
		}

		System.out.println("Finished processing images");
	}

	private static Collection<IPTCDataSet> getTags(File image) {
		// create collection to store all keywords found in an image
		Set<IPTCDataSet> keywords = new HashSet<>();

		// get results from clarifai api !Requires internet
		List<RecognitionResult> clarifaiTags =
			clarifai.recognize(new RecognitionRequest(image));

		// insert all tags returned as keyword, filter away the less likely tags
		clarifaiTags.get(0).getTags().stream().filter(tag -> tag.getProbability() > 0.95).forEach(tag -> {
			IPTCDataSet keyword = new IPTCDataSet(25, tag.getName());
			keywords.add(keyword);
		});

		return keywords;
	}

	private static void insertTags(File image, Collection<IPTCDataSet> keywords) throws IOException {
		// insert the keywords in an image and update it
		FileInputStream is = new FileInputStream(image);

		// create an output directory
		if (new File(image.getParent() + "/output/").mkdir())
			System.out.println("Created a directory: output");

		FileOutputStream os = new FileOutputStream(image.getParent() + "/output/" + image.getName());
		JPEGTweaker.insertIPTC(is, os, keywords, false);
	}
}
