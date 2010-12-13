package uk.org.openmentor.web;

import net.sourceforge.jwebunit.WebTestCase;

/**
 * The class <code>OpenMentorWebTestCase</code> currently test much of
 * the functionality of the completed application, concentrating on
 * simple things and security aspects.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version $Id$
 */
public class BaseWebTestCase extends WebTestCase {

    /** A user who will authenticate and be able to choose a course. **/
    String username = "OMUser";

    /** Her password **/
    String password = "openmentor";

    /** A user who will authenticate and be able to choose a course. **/
    String adminName = "admin";

    /** Her password **/
    String adminPassword = "WattL0ck";

    /** Decorator title prefix.  The String set in the main decoirator
     * which is prepended to all the actual page titles. **/
    String titlePrefix = "OpenMentor -  ";

    /**
     * Creates a new <code>BaseWebTestCase</code> instance.  The
     * port would be *much* better configured somewhere else, but I
     * don't know how to do it yet.
     *
     * @param name a <code>String</code> value
     */
    public BaseWebTestCase(String name) {
        super(name);
        int port=8080;
        getTestContext().setBaseUrl("http://localhost:" + port
                                    + "/openmentor");
    }

    /**
     * The <code>loginAction</code> helper method Just logs in with
     * the given credentails.
     *
     * @param username the user to log in;
     * @param password her password.
     */
    void loginAction(String username, String password) {
        assertFormPresent("loginForm");
        assertFormElementPresent("j_username");
        assertFormElementPresent("j_password");
        setFormElement("j_username", username);
        setFormElement("j_password", password);
        submit();
    }

    /**
     * The <code>loginAsUser</code> gets us started as the normal user.
     * the given credentails.
     *
     */
    void loginAsUser() {
        beginAt("/signin.jsp");
        loginAction(username,password);
    }

    /**
     * The <code>loginAsAdmin</code> gets us started as the admin user.
     *
     */
    void loginAsAdmin() {
        beginAt("/signin.jsp");
        loginAction(adminName,adminPassword);
    }
}

