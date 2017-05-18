package com.example.priceexplorer.rest.admin;

import com.example.priceexplorer.core.ChannelRegistry;
import com.example.priceexplorer.service.TmpFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * REST interface for Channel Administration. It provides means for
 * listing, regestering and deleting channel packages.
 *
 * Created by eugene on 14/05/17.
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/packages")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChannelAdminController {

    private final ChannelRegistry channelRegistry;

    private final TmpFileService tmpFileService;

    @GetMapping
    @ResponseStatus(OK)
    public List<PackageResource> listPackages() {
        return channelRegistry.listChannelPackages().map(PackageResource::new).collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public PackageResource registerPackage(@Valid @RequestBody PackageRegistrationRequest request)
            throws IOException {

        channelRegistry.register(request.getName(), tmpFileService.findFile(request.getFile()));

        return new PackageResource(request.getName());
    }

    @ResponseStatus(OK)
    @GetMapping("/{packageName}")
    public PackageResource getPackage(final @PathVariable String packageName) {
        return channelRegistry.listChannelPackages()
                .filter(packageName::equals)
                .map(PackageResource::new)
                .findFirst()
                .orElseThrow(PackageNotFoundException::new);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{packageName}")
    public void deregisterPackage(@PathVariable String packageName) {
        channelRegistry.deregister(packageName);
    }

    @ExceptionHandler(PackageNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public void packageNotFound() {}
}
