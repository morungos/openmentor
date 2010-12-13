package uk.org.openmentor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class <code>LoginWebTestCase</code> currently test much of
 * the functionality of the completed application, concentrating on
 * simple things and security aspects.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version $Id$
 */
public class LoginWebTestCase extends BaseWebTestCase {

    private static Log log = LogFactory.getLog(LoginWebTestCase.class);

    /**
     * Creates a new <code>LoginWebTestCase</code> instance.
     * @param name a <code>String</code> value
     */
    public LoginWebTestCase(String name) {
        super(name);
    }

    /**
       If we go directly to the login page, we expect a correct login
       to pass to the index page, but with the user name set
       correctly.
    **/
    public void testLoginPage() {
        loginAsUser();
        assertTitleEquals(titlePrefix + "Welcome");
        assertTextPresent(username);
    }

    /**
       If we go directly to the login page, we expect a correct login
       to pass to the index page, but with the user name set
       correctly.
    **/
    public void testAdminLogin() {
        loginAsAdmin();
        assertTitleEquals(titlePrefix + "Welcome");
        assertTextPresent(adminName);
    }

    /**
     * If we give an incorrect user id or password we should be told
     * about it and invited to try agian.
     */
    public void testWrongPassword() {
        beginAt("/signin.jsp");
        loginAction(username,"junk");
        assertTextPresent("problem with your login");
        assertLinkPresentWithText("try again");
        clickLinkWithText("try again");
        assertFormPresent("loginForm");
    }
    /**
     * The <code>testForceLoginAsUser</code> method checks that an
     * attempt to access a protected page will force redirection to
     * the login page, but will eventually get to the requested page.
     *
     */
    public void testForceLoginAsUser() {
        beginAt("/");
        assertLinkPresentWithText("Choose course");
        clickLinkWithText("Choose course");
        //We should be forced to the login page.
        assertTitleEquals(titlePrefix + "Sign in to Open Mentor");
        loginAction(username,password);
        // And the go back to the page we requested.
        assertTextPresent("Choose a course");
    }
    /**
     * The <code>testForceLoginAsUser</code> method checks that an
     * attempt to access a protected page will force redirection to
     * the login page, but next time round, will allow acccess.  TODO
     * - I can't even check I get a 403 exception.  Need to sort out
     * logging?
     */
    public void testForceLoginAsAdmin() {
        loginAsUser();
        //Try to access a forbidden resource
        assertLinkPresentWithText("Site index");
        try {
            clickLinkWithText("Site index");
            fail("Should have got a 403 error");
        } catch (Exception e) {
            log.error("Found " + e);
            // We expect a 403 exception
        }
        /*        // Expect a complaint and redirection
        assertTextPresent("don't have an appropriate permission");
        clickLinkWithText("signin");
        // Now login with the correct credentials
        loginAction(adminName,adminPassword);
        // And try again
        clickLinkWithText("Site index");
        // It is OK now
        assertTextPresent("Site Index"); */
    }
}

