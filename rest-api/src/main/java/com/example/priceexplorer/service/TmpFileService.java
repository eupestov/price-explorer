package com.example.priceexplorer.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Stores uploaded files in a temporary folder.
 *
 * Created by eugene on 18/05/17.
 */
@Service
public class TmpFileService {

    private final Path tmpLocation = Files.createTempDirectory("pc-tmp-jars-");

    public TmpFileService() throws IOException {
    }


    public void store(final InputStream inputStream, final String filename) throws IOException {
        Files.copy(inputStream, this.tmpLocation.resolve(filename), REPLACE_EXISTING);
    }

    public Path findFile(final String fileName) {
        return tmpLocation.resolve(fileName);
    }
}
