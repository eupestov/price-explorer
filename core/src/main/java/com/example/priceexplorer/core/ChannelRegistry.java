package com.example.priceexplorer.core;

import com.example.priceexplorer.spi.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by eugene.
 */
@Slf4j
@Service
public class ChannelRegistry {

    private static final PathMatcher JARS =
            FileSystems.getDefault().getPathMatcher("glob:**.jar");

    private final Path jarLocation;

    private volatile List<Channel> registeredChannels;

    public ChannelRegistry() throws IOException {
        jarLocation = Files.createTempDirectory("channel-libs-");
        registeredChannels = Collections.emptyList();
    }

    public List<Channel> getRegisteredChannels() {
        return registeredChannels;
    }

    public Stream<String> listChannelPackages() {
        try {
            return Files.find(jarLocation, 1, (path, attributes) -> JARS.matches(path))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(s -> s.substring(0, s.length() - 4));
        } catch (IOException e) {
            throw new ChannelRegistryException("Failed to list jar files", e);
        }
    }

    public synchronized void register(final String packageName, final Path jarFile) {
        try {
            Files.move(jarFile, this.jarLocation.resolve(packageName + ".jar"), REPLACE_EXISTING);
            reloadChannels();
        } catch (IOException e) {
            throw new ChannelRegistryException("Failed to move jar file " + jarFile, e);
        }
    }

    public synchronized void deregister(final String packageName) {
        try {
            Files.deleteIfExists(this.jarLocation.resolve(packageName + ".jar"));
            reloadChannels();
        } catch (IOException e) {
            throw new ChannelRegistryException("Failed to delete jar file " + packageName, e);
        }
    }

    private static URL toURL(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            throw new ChannelRegistryException("Converstion to URL failed for" + uri);
        }
    }

    private void reloadChannels() {
        try {

            URL[] jarUrls = Files.find(jarLocation, 1, (path, attrs) -> JARS.matches(path))
                    .map(Path::toUri).map(ChannelRegistry::toURL).toArray(URL[]::new);

            ServiceLoader<Channel> serviceLoader =
                    ServiceLoader.load(Channel.class, new URLClassLoader(jarUrls));

            List<Channel> channels = new LinkedList<>();
            for (Channel channel : serviceLoader) {
                channels.add(channel);
            }

            registeredChannels = Collections.unmodifiableList(channels);

        } catch (IOException e) {
            throw new ChannelRegistryException("Failed to list jar files", e);
        }
    }
}
