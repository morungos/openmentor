package uk.org.openmentor.report.exceptions;

import uk.org.openmentor.businesslogic.BusinessLogicException;

/**
 * Exception class to indicate a problem has occured while trying to create a
 * chart.
 * 
 * @author Openmentor team
 */

public class ChartCreationException extends BusinessLogicException {
    
    static final long serialVersionUID = -7363087627875291921L;
    
    /**
     * Constructs the exception and specifies the message to pass with the
     * exception.
     *
     * @param message
     *            The message to pass with the exception
     */

    public ChartCreationException(String message) {
        super(message);
    }
}
