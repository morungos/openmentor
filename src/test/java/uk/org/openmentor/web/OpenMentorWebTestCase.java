package uk.org.openmentor.web;

/**
 * The class <code>OpenMentorWebTestCase</code> currently test much of
 * the functionality of the completed application, concentrating on
 * simple things and security aspects.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version $Id$
 */
public class OpenMentorWebTestCase extends BaseWebTestCase {

    /**
     * Creates a new <code>OpenMentorWebTestCase</code> instance.  The
     * port would be *much* better configuerd somewhere else, but I
     * don't know how to do it yet.
     *
     * @param name a <code>String</code> value
     */
    public OpenMentorWebTestCase(String name) {
        super(name);
    }

    /**
     * The <code>testIndexPage</code> method ensures that all the
     * unprotected links are present and are working
     */
    public void testIndexPage() {
        beginAt("/");
        assertTitleEquals("OpenMentor -  Welcome");
        // Check anonymous login has worked.
        assertTextPresent("No current course");
        assertTextPresent("visitor");
        assertLinkPresentWithText("Background");
        clickLinkWithText("Background"); 
        assertTitleEquals(titlePrefix + "Background");
    }


    /**
     * The <code>testUploadPage</code> first checks that course
     * selection behaves correctly and then starts looking at the
     * assignment upload form.
     *
     */
    public void testUploadPage() {
        // First login as an empowered user 
        loginAsUser();
        // By default no course is selected, so select one.
        assertTextPresent("No current course");
        assertLinkPresentWithText("Choose course");
        clickLinkWithText("Choose course");
        assertFormPresent("selectCourses");
        checkCheckbox("course","CM2006");
        submit();
        // And check the selection succeeds
        assertTextPresent("Current course: CM2006");
        clickLinkWithText("Upload an assignment"); 
        assertTextPresent("Submit an assignment");
    }

}
