package uk.org.openmentor.report.exceptions;

import uk.org.openmentor.businesslogic.BusinessLogicException;

/**
 * Reports errors due to invalid connections in the report parser.
 *
 * @author OpenMentor team
 */

public class InvalidConnectionException extends BusinessLogicException {
    
    static final long serialVersionUID = -7737168461656919843L;
    
    /**
     * Construct the exception with a given message to pass back
     */

    public InvalidConnectionException(String message) {
        super(message);
    }
}
