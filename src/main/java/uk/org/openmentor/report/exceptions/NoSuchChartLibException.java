package uk.org.openmentor.report.exceptions;

import uk.org.openmentor.businesslogic.BusinessLogicException;

/**
 * Exception class to indicate that the specified charting library hasn't been
 * found during initialisation or use of a charting factory
 * 
 * @author OpenMentor team
 */

public class NoSuchChartLibException extends BusinessLogicException {
    
    static final long serialVersionUID = -172414568806307735L;
    
    /**
     * Constructs the exception and specifies the message to pass with the
     * exception.
     *
     * @param message
     *            The message to pass with the exception
     */

    public NoSuchChartLibException(String message) {
        super(message);
    }
}
