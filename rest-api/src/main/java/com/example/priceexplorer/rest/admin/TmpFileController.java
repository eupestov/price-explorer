package com.example.priceexplorer.rest.admin;

import com.example.priceexplorer.service.TmpFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller for temporary file uploads.
 *
 * Created by eugene on 18/05/17.
 */
@RestController
@RequestMapping("/api/admin/tmp/files")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmpFileController {

    private final TmpFileService tmpFileService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
        }
        try {
            tmpFileService.store(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
