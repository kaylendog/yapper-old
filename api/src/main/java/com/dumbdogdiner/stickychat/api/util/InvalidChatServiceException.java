package com.dumbdogdiner.stickychat.api.util;

/**
 * Error thrown when the StickyChat API is unavailable or of an illegal type.
 */
public class InvalidChatServiceException extends RuntimeException {
    public InvalidChatServiceException(String exception){
        super(exception);
    }
}
