/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messagePackage;

/**
 *
 * @author jjor1
 */
public enum MessageType {
    MEETING ("Meeting"),
    LECTURE ("Lecture"),
    PRACTICAL ("Practical");
    
    private final String name;
    
    MessageType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
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
