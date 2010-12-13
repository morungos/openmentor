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

package uk.org.openmentor.model.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import uk.org.openmentor.model.Comment;

/**
 * I'm still learning to write mocks; so let me start with a very
 * naive one, which I nevertheless believe will be useful.  The first
 * 16 comments generated come from a single submission.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class MockComment extends Comment{

    public static final String[] texts = {
        "What is this based on?",
        "Certainly a basic computer literacy level would have to be"
        + " determined.  But you might also consider integrating the"
        + " other skills with particular learning tasks.  In Block 2"
        + " you'll see that the Resources pages take you to the OU online"
        + " library, so in order to get the resource you need for the"
        + " conference or TMA you are also learning how an online library"
        + " works.",
        "Good idea. It's helpful to watch/video individual students using"
        + " the materials and then question them directly after they have"
        + " completed a task.",
        "Similarly, you need a citation here: (Doolan, 2004)",
        "Did they all tend to be this specific?",
        "Good idea as sometimes the dyslexia has not been recognised earlier.",
        "My note about the Royal College of Art was only something I'd heard"
        + " from one person, but it's easy enough to check.",
        "It is a popular notion that teaching that supports dyslexics is good"
        + " for non-dyslexics too because it is based on multi-sensory"
        + " (aural, visual, kinaesthetic) methods.  It would be interesting"
        + " to test this idea out.",
        "If you hadn't specified computers, would it have been clear what you"
        + " were asking? What other facilities might there be? ",
        "See page 10 of the Assignment Guide",
        "Page 11 of the Assignment Guide shows how to list the Overview"
        + " Essays.",
        "Very good introduction.  You've placed your course in context and"
        + " explained specifically where the discussion will go.",
        "Here you should have a citation.  Did you get this from a particular"
        + " report?",
        "Good idea.  You'll find some guidance here in the Block 4 resources.",
        "Page 12 of the Assignment Guide shows how to cite conference"
        + " messages.",
        "I like the way you've used footnotes to explain your acronyms."
        + "  Good idea."
    };

    private static final String[][] categories = {
        {"C1"}, {"B1"}, {"A1"}, {""}, {"C1"}, {"A1"}, {"B1"}, {"B5", "A1"},
        {"C1", "B2", "C2"}, {""}, {"B3"}, {"A1", "B1"}, {"C1", "B1"}, {"A1"},
        {"B3"},{"A1", "B2", "B1"} 
    };

    public static final String[] texts2 = {
        "Would they be close enough geographically?  You haven't"
        + " established that this would just be on offer only for those"
        + " in the area of the university. From your description of your"
        + " learners, a meeting as an effort to counter isolation and"
        + " encourage interaction with known individuals would probably"
        + " be welcome.  There is a strong argument against using these"
        + " sessions to introduce learners to the technology.  Some"
        + " activities done on the computer they will be using, with"
        + " synchronous telephone and e-mail support, is a more authentic"
        + " learning environment.",
        "Including assessment which, from your description, will probably"
        + " be important to your learners.",
        "Its good to see you found this resource useful.",
        "Well argued.",
        "Do you mean ?state' here, that is, funded by public money rather"
        + " than private?",
        "On this subject or something similar?  It would be good to know"
        + " what lessons you have learned from these experiences.",
        "If the exam structure for students is still the same, will the"
        + " teachers be encouraged to implement this new learning into"
        + " their classroom practice?",
        "This is a good summary.  You also need to note down which areas"
        + " you will need to explore further.",
        "Not a word wasted here! Good introduction, concise but with all"
        + " relevance information included. It would also be good to add"
        + " here a statement about the source of your knowledge about your"
        + " learners. I realise that some of this is woven into your"
        + " discussion.",
        "Pre-assessment of what they can actually do will be important.",
        "So they could probably tell you what constitutes a good experiment"
        + " but would not have designed and carried one out themselves?"
        + "  And while they may not have been encouraged to reflect on their"
        + " practice, in the right environment or with appropriate rewards"
        + " they might adapt quite easily to a constructivist learning"
        + " environment. This would be interesting to test out."
    };

    private static final String[][] categories2 = {
        {"C1"},{"B1", "B5"}, {"A1"}, {""}, {"C1"}, {"C1", "B5"}, {"C2", "C1"},
        {"B1", "A1", "B5"}, {"D1", "B1", "B5"}, {"B5"}, {"B1", "C1", "B5"}
    };

    /** Repreesnts a submission with 20 comments. */
    public static final String[] texts3 = {
        "We usually talk about learning in general being the pull whereas"
        + " if we use technology as the argument for implementation then"
        + " that becomes a technology push.",
        "I think you need to expand this a little just to link the situated"
        + " learning in his is about stressing the communicational aspects of"
        + " learning online both synchronously and asynchronously.",
        "What exactly will you blend together it is not too clear. It is"
        + " very good practice to read through your work and number of times"
        + " to make sure you are saying what you mean. If you have a tame"
        + " proof reader who is constructively critical that too can help"
        + " in ensuring that you are getting your ideas over to the reader.",
        "As little training is really open perhaps you should be using DL"
        + " for distance learning rather than ODL.",
        "Books? Perhaps on some very early form or generations of distance"
        + " learning, but in later generations perhaps there is a wider mix"
        + " of material and learning objects.",
        "You may need to adopt a slightly more critical approach to this"
        + " strong behaviourally measured view of learning. In training this"
        + " may well be the case but in education like your own learning on"
        + " this course you may well be learning something perhaps about"
        + " constructivism, not necessarily changing your behaviour but it"
        + " may well be discernable in the approach you take to your TMAs.",
        "These may well be issues or details about the proposed course but"
        + " whether they are about the learners themselves or their approach"
        + " I am not too sure, so are you infact addressing the question as"
        + " set? You may be but it needs to be couched in terms which relates"
        + " to the question.",
        "Again I'm not too sure just what you mean here?",
        "Are these your fellow students on H804?",
        "Yes good point and they often also have a conflicting interest in"
        + " meeting work deadlines rather than meeting the developmental"
        + " needs of their staff.",
        "This could have done with a little academic distance eg you might"
        + " have said that learning has been defined in many ways but for"
        + " this exercise you were going to use Bass and Vaughan's approach"
        + " to it being about enabling permanent chages in behaviour or"
        + " belief.",
        "This is not in the references",
        "I'm not too sure whether you have resolved the thinking in this"
        + " paragraph.",
        "Is this adapting to the different and evolving styles of learning"
        + " as they develop  within the learner as they mature as learners?",
        "So you are aiming to offer a blended approach to the delivery of"
        + " your course?",
        "Where does this one come from?",
        "You nearly get there here so take the previous comment lightly"
        + " although the constructivist paradigm does map onto McGivney's"
        + " tripartite taxonomy.",
        "Well rounded off and a good reference back to your original point"
        + " about learning being measured in changes of behaviour. I would"
        + " of course take issue with the strength of this behaviourist view"
        + " but nevertheless is makes the point.",
        "You might need to be a little more explicit: technology enhancement"
        + " is what I think you are referring to  so as lifelong learning"
        + " becomes more critical in terms of keeping abreast of a rapidly"
        + " changing world, we need to develop skills to continue learning"
        + " via an ever changing and converging technological"
        + " infrastructure.",
        "You might have said  egos the practice is to use the technology"
        + " to push the uptake of e-learning by delivering bite sized"
        + " training chunks in the hope that this will encourage uptake'"
        + " or words to that effect."
    };

    private static final String[][] categories3 = {
        {""}, {"B1", "B5"}, {"B1", "C1", "A1"}, {"B1"}, {"B1", "C1"},
        {"D1", "B1", "B2"}, {"B1", "C1", "B5"}, {"C1", "B5"}, {"C1"},
        {"B1", "A3", "A1"}, {"B1", "A3"}, {"B1", "B5"}, {"B5"}, {"C1"},
        {"C1", "B5"}, {"C1"}, {""}, {"D1", "B1"}, {"B1", "B5"}, {"B1"}
    };

    public MockComment(String comment, String[] categories) {
        super();
        this.setText(comment);
        Set<String> s = new HashSet<String>();
        s.addAll(Arrays.asList(categories));
        this.setClasses(s);
    }

    public static final Comment getComment(int i) {
        return new MockComment(texts[i],categories[i]);
    }

    public static final Comment getComment2(int i) {
        return new MockComment(texts2[i],categories2[i]);
    }

    public static final Comment getComment3(int i) {
        return new MockComment(texts3[i],categories3[i]);
    }
}
