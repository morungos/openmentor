/* =======================================================================
 * Copyright 2004-2006 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */

package uk.org.openmentor.model;

import java.io.Serializable;
import java.util.Set;

/**
 * The model class used to represent comments in Open Mentor.
 * This class can be persisted through whatever persistence
 * system you choose to use. This is at the bottom of the
 * submission object structure; it used to represent an individual
 * comment within a submission. 
 * 
 * @author Stuart Watt
 */
public class Comment implements Serializable {
    
    /** The serialisation identifier. */
    static final long serialVersionUID = 7927447323605386267L;

    /** Identifier field. */
    private int id;

    /** The comment text. */
    private String text;

    /** The set of comment classes. */
    private Set<String> classes;

    /**
     * Constructs a new Comment object, given the comment text and a set of
     * comment classes.
     * @param newText       the comment text
     * @param newClasses    the comment classes
     */
    public Comment(final String newText, final Set<String> newClasses) {
        this.text = newText;
        this.classes = newClasses;
    }

    /**
     * Constructs a new Comment object.
     */
    public Comment() {
    }

    /**
     * Constructs a new Comment object, given a set of comment
     * classes.
     * @param newClasses    the comment classes
     */
    public Comment(final Set<String> newClasses) {
        this.classes = newClasses;
    }

    /**
     * Gets the comment identifier.
     * @return                  the comment identifier
     */
    public final int getId() {
        return this.id;
    }

    /**
     * Sets the comment identifier.
     * @param newId             the new comment identifier
     */
    public final void setId(final int newId) {
        this.id = newId;
    }

    /**
     * Gets the comment text.
     * @return                  the comment text
     */
    public final String getText() {
        return this.text;
    }

    /**
     * Sets the comment text.
     * @param newText           the new comment text
     */
    public final void setText(final String newText) {
        this.text = newText;
    }

    /**
     * Gets the comment classes.
     * @return                  the comment classes
     */
    public final Set<String> getClasses() {
        return this.classes;
    }

    /**
     * Sets the comment classes.
     * @param newClasses        the new comment classes
     */
    public final void setClasses(final Set<String> newClasses) {
        this.classes = newClasses;
    }
}
