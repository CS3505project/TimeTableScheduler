/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

/**
 * Holds an array of entrys that contain an error message and a boolean value
 * to indicate if the entry is valid or not. Used for validating form data
 * 
 * @author John O Riordan
 */
public class DataEntry {
    private Entry[] data;
    
    /**
     * Sets the number of entries to store
     * @param length 
     */
    public DataEntry(int length) {
        data = new Entry[length];
        for (int i = 0; i < length; i++) {
            data[i] = new Entry();
        }
    }
    
    /**
     * Checks if the data entry is valid
     * @param index index of the data
     * @return true if valid
     */
    public boolean isValidEntry(int index) {
        return data[index].isValidData();
    }
    
    /**
     * Returns the number of errors
     * @return number of errors
     */
    public int numErrors() {
        int errors = 0;
        for (int i = 0; i < data.length; i++) {
            if (!data[i].getErrorMessage().equals("")) {
                errors++;
            }
        }
        return errors;
    }
    
    /**
     * Sets whether the entry is valid or not
     * @param index index of the entry
     * @param valid valid entry indicator 
     */
    public void setValidEntry(int index, boolean valid) {
        data[index].setValidData(valid);
        data[index].setErrorMessage("");
    }
    
    /**
     * Clears all the data from the entry and sets the array back to false
     */
    public void clear() {
        for (int i = 0; i < data.length; i++) {
            data[i].setErrorMessage("");
            data[i].setValidData(false);
        }
    }
    
    /**
     * Gets the error messages as list seperated by a html break tag for displaying
     * @return error messages
     */
    public String getErrorMessages() {
        String errors = "";
        for (int i = 0; i < data.length; i++) {
            if (!data[i].isValidData()) {
                errors += data[i].getErrorMessage() + "<br>";
            }
        }
        return errors;
    }
    
    /**
     * Inserts an error message at the specified index
     * @param index index for the message
     * @param message the error message
     */
    public void addErrorMessage(int index, String message) {
        data[index].setValidData(false);
        data[index].setErrorMessage(message);
    }
    
    /**
     * Checks if the array is full of valid data entries
     * @return true if all valid data
     */
    public boolean isValid() {
        boolean valid = true;
        for (int i = 0; i < data.length; i++) {
            valid = valid && data[i].isValidData();
        }
        return valid;
    }
    
    /**
     * Represents a data entry
     */
    private class Entry {
        private String errorMessage = "";
        private boolean validData = false;

        public Entry() {}

        /**
         * Gets the error message in this entry
         * @return error message
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * Sets the error message for this entry
         * @param errorMessage Error message
         */
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        /**
         * Checks if the entry is valid
         * @return True if valid
         */
        public boolean isValidData() {
            return validData;
        }

        /**
         * Sets the entry to true or false
         * @param validData valid entry or not
         */
        public void setValidData(boolean validData) {
            this.validData = validData;
        }
    }
}
