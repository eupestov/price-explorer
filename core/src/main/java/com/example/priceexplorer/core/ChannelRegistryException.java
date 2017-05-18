package com.example.priceexplorer.core;

/**
 * Created by eugene.
 */
public class ChannelRegistryException extends RuntimeException {

    public ChannelRegistryException(final String message) {
        super(message);
    }

    public ChannelRegistryException(final String message, final Throwable ex) {
        super(message, ex);
    }

}
