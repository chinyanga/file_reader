package com.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class FileReader {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		String userDirectory = System.getProperty("user.dir") + "/src/resources/";
		System.out.println("=================Reading files from resources folder of the project===================== \n");
		System.out.println("Enter filename for the first file:");
		String file1Name = userDirectory + in.nextLine().trim();
		System.out.println("Enter filename for the second file:");
		String file2Name = userDirectory + in.nextLine().trim();
		Map<String, String> ipMap = fileReader(file1Name, file2Name);
		printOutput(ipMap);
		in.close();
		System.out.println("====================================== \n");
	}

	public static Map<String, String> fileReader(String file1Path, String file2Path) {

		Scanner sc = null;
		Map<String, String> ipMap = new TreeMap<>();
		for (int i = 0; i < 2; i++) {
			System.out.printf("\n............Reading %s ...........", i == 0 ? file1Path : file2Path);
			System.out.println();

			File file = new File(i == 0 ? file1Path : file2Path);
			try {
				sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String input = sc.nextLine().trim();
					if (input.trim().length() > 0) {
						System.out.println(input);
						if (input.split(":").length == 2) {
							String ipAddress = input.split(":")[0].trim();
							String ipValues = input.split(":")[1].trim();
							if (ipMap.containsKey(ipAddress)) {
								ipMap.put(ipAddress, ipMap.get(ipAddress) + "," + ipValues);
							} else {
								ipMap.put(ipAddress, ipValues);
							}
						}
					}
				}
				System.out.printf("............Done Reading %s ...........\n", i == 0 ? file1Path : file2Path);
			} catch (FileNotFoundException e) {
				System.out.printf("Error Reading %s ...........\n", i == 0 ? file1Path : file2Path);
				e.printStackTrace();
			}

			System.out.println();
		}
		sc.close();
		return ipMap;
	}

	public static void printOutput(Map<String, String> ipMap) {
		System.out.println("\n.....................Formatting output .............\n");
		for (Entry<String, String> entry : ipMap.entrySet()) {
			String[] ipValues = entry.getValue().trim().split(",");
			Arrays.sort(ipValues);
			Set<String> ipValueSet = new HashSet<>(Arrays.asList(ipValues));
			System.out.printf("%s:\t%s", entry.getKey(), String.join(",", ipValueSet));
			System.out.println();
		}
	}

}
