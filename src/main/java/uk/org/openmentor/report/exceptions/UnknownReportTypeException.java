package uk.org.openmentor.report.exceptions;

import uk.org.openmentor.businesslogic.BusinessLogicException;

/**
 * Provides an exception type to indicate that the requested report type isn't
 * known to the report request parser
 * 
 * @author OpenMentor team
 */

public class UnknownReportTypeException extends BusinessLogicException {
    
    static final long serialVersionUID = 4060957182090655572L;
    
    /**
     * Construct with the message that should be passed as part of the exception
     * 
     * @param message
     *            the message to pass with the exception
     */

    public UnknownReportTypeException(String message) {
        super(message);
    }
}
