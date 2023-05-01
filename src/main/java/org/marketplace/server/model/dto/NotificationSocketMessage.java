package org.marketplace.server.model.dto;

import org.marketplace.server.model.MessageType;

/**
 * Class used to store notification message
 */

public class NotificationSocketMessage {
    private String message;
    private MessageType messageType;

    public NotificationSocketMessage(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage(){
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
