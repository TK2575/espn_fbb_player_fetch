package dev.tk2575.fantasybaseball;

import lombok.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Utils {

	public static List<CSVFile> readCSVFilesInDirectory(@NonNull String directory) {
		List<CSVFile> files = new ArrayList<>();
		ClassLoader classLoader = Utils.class.getClassLoader();
		InputStream directoryStream = classLoader.getResourceAsStream(directory);

		if (directoryStream != null) {
			List<String> fileNames =
					new BufferedReader(new InputStreamReader(directoryStream))
							.lines()
							.filter(each -> each.endsWith(".csv"))
							.collect(toList());


			InputStream fileStream;
			for (String each : fileNames) {
				fileStream = classLoader.getResourceAsStream(String.join("/", directory, each));
				if (fileStream != null) {
					files.add(new CSVFile(each, new BufferedReader(new InputStreamReader(fileStream)).lines().collect(joining("\n"))));
				}
			}
		}

		return files;
	}
}
