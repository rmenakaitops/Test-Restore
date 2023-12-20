package com.mitra.middleware.testsupportRouter.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {
	
	private static final String RESOURCE_DIR = "src/cucumber/resources/";

	public static String readResourceContent(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCE_DIR + filename)));
    }
}