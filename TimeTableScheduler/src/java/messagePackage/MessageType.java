package messagePackage;

/**
 * Type of messages that can be sent to users
 * 
 * @author John O Riordan
 */
public enum MessageType {
    MEETING ("Meeting"),
    LECTURE ("Lecture"),
    PRACTICAL ("Practical");
    
    private final String name;
    
    MessageType(String name) {
        this.name = name;
    }
    
    /**
     * Gets the name for this message type
     * @return Message type name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Converts the name for a message type to a MessageType object
     * @param messageType Type name
     * @return Message type object
     */
    public static MessageType convertToMessage(String messageType) {
        MessageType type = MessageType.MEETING;
        for (MessageType message : MessageType.values()) {
            if (message.name.equals(messageType)) {
                type = message;
            }
        }
        return type;
    }
}
