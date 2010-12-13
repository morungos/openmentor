/**
 * @author OpenMentor team
 */

package uk.org.openmentor.report.exceptions;

import uk.org.openmentor.businesslogic.BusinessLogicException;

/**
 * Exception class used for errors where a mime type is requested that is not
 * supported by a particular chart implementation
 */

public class ImageTypeNotSupportedException extends BusinessLogicException {
    
    static final long serialVersionUID = 3433406900717963295L;
    
    /**
     * Creates an instance of the exception with a specified message.
     *
     * @param message
     *            The message to forward with the exception
     */

    public ImageTypeNotSupportedException(String message) {
        super(message);
    }
}
