package otto.contacts.app;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * This is a pojo container for Contact information. It is also serializable to
 * help transport using intents. This contains database references and a photo object.
 *
 */
public class Contact implements Serializable,ListHeaderInterface {
    public int lookUpKey;
    public String displayName;
    public String homePhoneNumber;
    public String cellPhoneNumber;
    public String workPhoneNumber;
    public String primaryEmail;
    public String contactID;

    public int mIdIndex;
    public String mCurrentLookupKey;
    public long mCurrentId;
    public String mSelectedContactUri;
    private boolean dud = false;
    private char dudChar;
    private Photo photo; // This is an object that contains all information about our photo.

        /**
         * Constructor for a standard contact object
         * @param contactID Database Id for said contact.
         * @param contactName Name of contact
         * @param  lookupKey Lookup-key of contact
         * @param  photo A photo object to be displayed if user wishes to see detailed info about contact.
         * @param  s home Phone number.
         * @param  s1 Cell Phone Number
         * @param  s2 Work Phone Number
         * @param  primaryEmail Primary Email of contact.
         * @param mSelectedContactUri URI of contact.
         * **/
        public Contact(String contactID, String contactName, int lookupKey, Photo photo, String s, String s1, String s2, String primaryEmail,String mSelectedContactUri)
        {
            this.contactID = contactID;
            this.mSelectedContactUri = mSelectedContactUri;
            this.lookUpKey = lookupKey;
            this.displayName = contactName;
            this.homePhoneNumber = s;
            this.cellPhoneNumber = s1;
            this.workPhoneNumber = s2;
            this.primaryEmail = primaryEmail;
            this.photo = photo;
        }
    public Contact(char a)
    {

    }

    /**
     * Getter for contact's photo
     * @return  Photo object.
     * **/
    public Photo getPhoto()
    {
        return this.photo;
    }

    /**
     * Getter for contact's lookUpKey
     * @return  lookupKey integer.
     * **/

    public int getLookUpKey()
    {
        return  this.lookUpKey;
    }


    /**
     * Used for type inferrence in composite design architecture. Always returns false
     * @return false
     * **/
    @Override
    public boolean isSectionHeader() {
        return false;
    }

    /**
     * Used for type inferrence in composite design architecture. Always returns false
     * @return false
     * **/
    @Override
    public boolean searchFor() {
        return false;
    }

    /**
     * Used for type inferrence in composite design architecture. Always returns True
     * @return true
     *
     * **/
    @Override
    public boolean isContact() {
        return true;
    }

    /**
     * Used for type inferrence in composite design architecture. Always returns false
     * @return false
     * **/
    @Override
    public boolean isGroup() {
        return false;
    }

    /**
     * Used for type extraction. Returns this contact to the class that is referencing it.
     * @return this
     * **/
    @Override
    public Contact getContact() {
        return this;
    }


    /**
     * Used for type exatraction. Returns null because this is not a SectionItem object
     * @return null
     * **/
    @Override
    public SectionItem getSectionHeader() {
        Log.d("Contact", "Cannot get sectionHeader information from contact. Null returned");

        return null;
    }
/**
 * Used for type extraction. Returns null because this is not a Group object.
 **/
    @Override
    public Group getGroup() {
        return null;
    }
}
