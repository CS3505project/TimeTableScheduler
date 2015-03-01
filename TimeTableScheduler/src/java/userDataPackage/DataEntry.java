/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

/**
 *
 * @author jjor1
 */
public class DataEntry {
    private Entry[] data;
    
    public DataEntry(int length) {
        data = new Entry[length];
        for (int i = 0; i < length; i++) {
            data[i] = new Entry();
        }
    }
    
    public int numErrors() {
        int errors = 0;
        for (int i = 0; i < data.length; i++) {
            if (!data[i].getErrorMessage().equals("")) {
                errors++;
            }
        }
        return errors;
    }
    
    public void setValidEntry(int index, boolean valid) {
        data[index].setValidData(valid);
        data[index].setErrorMessage("");
    }
    
    public void clear() {
        for (int i = 0; i < data.length; i++) {
            data[i].setErrorMessage("");
            data[i].setValidData(false);
        }
    }
    
    public String getErrorMessages() {
        String errors = "";
        for (int i = 0; i < data.length; i++) {
            if (!data[i].isValidData()) {
                errors += data[i].getErrorMessage() + "<br>";
            }
        }
        return errors;
    }
    
    public void addErrorMessage(int index, String message) {
        data[index].setValidData(false);
        data[index].setErrorMessage(message);
    }
    
    public boolean isValid() {
        boolean valid = true;
        for (int i = 0; i < data.length; i++) {
            System.out.println(i + " = " + data[i].isValidData());
            valid = valid && data[i].isValidData();
        }
        return valid;
    }
    
    private class Entry {
        private String errorMessage = "";
        private boolean validData = false;

        public Entry() {}

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public boolean isValidData() {
            return validData;
        }

        public void setValidData(boolean validData) {
            this.validData = validData;
        }
    }
}
