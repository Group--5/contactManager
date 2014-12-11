package otto.contacts.app;

import android.util.Log;

import java.util.List;

/**
 * This is a standard group Pojo. Works in the vain of our contact Pojo */
public class Group implements ListHeaderInterface{

    public String name;
    public String ID;
    public String IDS;


    /**Constructor for group object
     * @param id Group lookupID
     * @param name Group's name.
     * @param IDS Group URI
     * **/
     public Group (String id, String name , String IDS)
    {
    this.name = name;
    this.ID = id;

        String[] ids = id.split(",");
        for(String a: ids)
        {
            Log.d("TESTTEST",a + "||" + name + "||");

        }
    }

/**
 * Used for type-checking. Allways returns false;
 * @return false
 * **/
    @Override
    public boolean isSectionHeader() {
        return false;
    }

    /**
     * Used for type-chekcing. Always returns false
     * @return  false
     * **/
    @Override
    public boolean searchFor() {
        return false;
    }

    /**Used for type-checking. Always returns false
     * @return false
     * */
    @Override
    public boolean isContact() {
        return false;
    }

    /**Used for type-checking. Since this is a group object, this always returns true
     * @return true
     * **/
    @Override
    public boolean isGroup() {

        return true;
    }
    /**Used for type-extraction. Since this is not a contact object, this always returns null
     * @return  null**/
    @Override
    public Contact getContact() {
        return null;
    }
/**Used for type-extraction. Since this is not a section header, this always returns null
 * @return null**/
    @Override
    public SectionItem getSectionHeader() {
        return null;
    }

    /**
     Used for type-extraction. Since this IS a group objec, this will return this class,
     @return this**/
    @Override
    public Group getGroup() {
        return this;
    }
}
