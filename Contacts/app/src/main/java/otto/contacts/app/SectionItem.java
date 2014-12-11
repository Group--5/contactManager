package otto.contacts.app;

import android.util.Log;

/**
* Think of section item as the little red box that seperates different parts of the contact list.
 *If section of our list has contacts that start with a 'b' for example. Then that section is
 * prepended with a section item labled 'b'.
 */
public class SectionItem implements ListHeaderInterface
{
    public char sectionNameChar;
    public String sectionNameString;
    public String META;
    /** Standard contsructor, dictates what will be displayed in our section header element
     * @param s String representation of what is displayed in our section header**/
    public SectionItem(String s)
    {
        this.sectionNameChar = s.toLowerCase().toCharArray()[0];
        this.sectionNameString = s.toLowerCase();
    }
    /** Standard constructor, dictates what will be displayed in our section header element
     * Currently not used. Future refactoring will rely on this constructor over the other.
     * @param c Character representation of what is displayed in our section header
     * **/
    public SectionItem(char c)
    {
        this.sectionNameChar = String.valueOf(c).toLowerCase().toCharArray()[0];
        this.sectionNameString = String.valueOf(c).toLowerCase();
    }

    /**Used for type checking. This object is a section header. So the return value is true.
     * @return  TRUE**/

    @Override
    public boolean isSectionHeader() {
        return true;
    }


    /**Used for type checking. This object is a section header. So the return value is false*
     * @return  false*/
    @Override
    public boolean searchFor() {
        return false;
    }


    /**Used for type checking. This object is a section header. So the return value is false*
     * @return  false*/
    @Override
    public boolean isContact() {
        return false;
    }


    /**Used for type checking. This object is a section header. So the return value is false*
     * @return  false*/
    @Override
    public boolean isGroup() {
        return false;
    }

    /**Used for type-extraction. Since the type being extracted is not a section header,
     * this will always return a null value
     * @return null **/
    @Override
    public Contact getContact() {
        Log.d("Section Item", "Cannot get contact information from SectionItem. Null returned");
        return null;
    }

    /**Used for type-extraction. Since the type being extracted is a section header,
      * this method will always return its parent class.
     * @return this **/
    @Override
    public SectionItem getSectionHeader() {
        return this;
    }

    /**Used for type-extraction. Since the type being extracted is not a section header,
     * this will always return a null value
     * @return null **/
    @Override
    public Group getGroup() {
        return null;
    }
}
