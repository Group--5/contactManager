package otto.contacts.app;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the layer between a complicated android data-base, and a complicated app. This needs to
 * hide details and information about contact retreival and updating to make development easier.
 * Unfortunately, current versions of this class, can not properly update or save contacts.
* We have experimented with the intent method of updating, but have found that it is only capable of appending information
 * under WORK, HOME, and MOBILE lists without replacing content that is already there. Likely the other group is doing it this way as well.
 * Pictures do not always load as well. But will when I finish this app over the winter break.
 */
public class ContactIntermidiary {
    private Context mContext;


    /**
     * This constructor accepts a context to be stored as member variable of the Contact Intermediary class.
     * The Context is needed to refference a less abstract database of contact information.
     *
     *
     * @param context This object stores a refference to your current
     *                application context for contact loading operations.
     *                make sure you send the correct application context
     */
    public ContactIntermidiary(Context context)
    {
        mContext = context;
    }

    // This method fetches an indiscriminate list of contact object from our contact manager.
    // TODO: Build list via Streamer.

    /**
     * This Method returns a small list containing only those contacts that start with the character letter you choose.
     * This list is sorted alphabetically.
     *
     *  @param c     This character will dictate what sort of contactlist it returns.
     *  @return     Alphabetically sorted list of contacts starting with the character c
    **/
    public List<Contact> getListOfContacts(Character c)
    {List<Contact> contacts = getListOfContacts();
     List<Contact> returnContacts = new ArrayList<Contact>();
        for (Contact x: contacts)
        {
            //If some contact name starts witht he character that we sent it. Add it to the appropriate list.
            if(x.displayName.toCharArray()[0] == String.valueOf(c).toUpperCase().toCharArray()[0] ||
                    x.displayName.toCharArray()[0] == String.valueOf(c).toLowerCase().toCharArray()[0])
            {
                returnContacts.add(x);
            }
        }
        return  sortingHat(returnContacts);
    }

 /**
  * This method saves and creates a contact. You MUST pass a name. Emails MUST be formatted correctly.
  *
  * @param DisplayName The name of the Contact you're adding
  * @param MobileNumber The cell-phone or mobile number you're adding(can be null)
  * @param HomeNumber   The home-phone number you're adding(can be null)
  * @param WorkNumber   The Work-phone number you're adding(can be null)
  * @param emailID      The email you're adding(can be blank or null)
  * **/

public void createContact(String DisplayName, String MobileNumber, String HomeNumber, String WorkNumber, String emailID)
{
    ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

    ops.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .build());

    //------------------------------------------------------ Names
    if (DisplayName != null) {
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        DisplayName).build());
    }

    //------------------------------------------------------ Mobile Number
    if (MobileNumber != null) {
        ops.add(ContentProviderOperation.
                newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
    }

    //------------------------------------------------------ Home Numbers
    if (HomeNumber != null) {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());
    }

    //------------------------------------------------------ Work Numbers
    if (WorkNumber != null) {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());
    }

    //------------------------------------------------------ Email
    if (emailID != null) {
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());
    }



    // Asking the Contact provider to create a new contact
    try {
        mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(mContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

}

    public void updateContactWorkPhone (String contactId, String newNumber, Activity act)
            throws RemoteException, OperationApplicationException{

        //ASSERT: @contactId alreay has a work phone number
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String selectPhone = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?";
        String[] phoneArgs = new String[]{contactId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_WORK)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(selectPhone, phoneArgs)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumber)
                .build());
        act.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }
    /**
     * This updates a cellphone numbers for given contact.
     *
     * @param contactId the Id of contact you're adding. This is the lookup ID
     * @param newNumber a new Cell phone number to replace old one.
     * @param act       a reference to your current activity.
     * **/
    public void updateContactCellPhone (String contactId, String newNumber, Activity act)
            throws RemoteException, OperationApplicationException{

        //ASSERT: @contactId alreay has a work phone number
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String selectPhone = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?";
        String[] phoneArgs = new String[]{contactId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(selectPhone, phoneArgs)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumber)
                .build());
        mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }

    /**
     * This method updates Home Phone numbers for contacts.
     *
     * @param contactId the Id of contact you're adding. This is the lookup ID
     * @param newNumber a new Home phone number to replace old one.
     * @param context   a reference to your current context.
     **/
    public void updateContactHomePhone (String contactId, String newNumber, Context context)
            throws RemoteException, OperationApplicationException{

        //ASSERT: @contactId alreay has a work phone number
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String selectPhone = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?";
        String[] phoneArgs = new String[]{contactId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(selectPhone, phoneArgs)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumber)
                .build());
        mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }

    /**
     * This method return a list of groups sorted alphabetically by name
     *
     * @return List of all groups in group object form.
     * **/
    public List<Group> getListofGroups()
    {
        List<Group> returnGroup = new ArrayList<Group>();

        ContentResolver cr = mContext.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Groups.CONTENT_URI,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do
            {
                Log.d("Groups", "Iterate");

                String id = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Groups._ID));
                String displayName = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Groups.TITLE));

                Log.d("Groups", "ID: " + id  + " Name: " + displayName);

                returnGroup.add(new Group(id,displayName,""));
            }
            while(cursor.moveToNext());

        }
        else
        {Log.d("Groups", "Did not move to first");}
        cursor.close();
        return groupSort(returnGroup);
    }

    /**
     *This returns a list of all contacts sorted alphabetically by name
     *@return a sorted list of all contacts stored in Android's data manager.
     **/
    public List<Contact> getListOfContacts()
    {
        List<Contact> contacts = new ArrayList<Contact>();
        ContentResolver cr = mContext.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            ArrayList<String> alContacts = new ArrayList<String>();
            do
            {
                String tempEmail = "";
                String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); //Mindex
               int mContactID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int lookupKey = cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);

                String lookupKeyReal = cursor.getString(lookupKey);

                Long mCurrentId = cursor.getLong(mContactID);
                String mSelectedContactUri = ContactsContract.Contacts.getLookupUri(mCurrentId, lookupKeyReal).toString();

                Photo photo = getContactPhoto(cursor);
                Log.d("Check",contactName);
                String[] phone = {null,null,null};

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ contactID }, null);
                    while (pCur.moveToNext())
                    {
                        String currNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String type = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        pCur.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID);
                     //Home1 Cell2 Work3

                      if(type.equals("2")) {
                          phone[1] = currNum;
                      }else if(type.equals("1"))
                      {
                          phone[0] = currNum;
                      }
                        else if(type.equals("3"))
                      {
                          phone[2] = currNum;
                      }
                    }

                    Cursor cur1 = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{contactID}, null);
                    while (cur1.moveToNext()) {
                        //to get the contact names
                        String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        Log.e("Name :", name);
                        tempEmail = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                        //                String lookupKeyReal = cursor.getString(lookupKey);

//                Long mCurrentId = cursor.getLong(mContactID);
//                Uri mSelectedContactUri = ContactsContract.Contacts.getLookupUri(mCurrentId, lookupKeyReal);
                }
                    cur1.close();
                    Log.d("Check", "=====");
                    pCur.close();
                }
                if(!(contactName.toCharArray()[0] == '#')) {
                    Contact con = new Contact(contactID, contactName, lookupKey, photo, phone[0], phone[1], phone[2], tempEmail,mSelectedContactUri);
                    contacts.add(con);
                }
                }
            while (cursor.moveToNext()) ;
                 }
        return sortingHat(contacts);
    }


    /**
     * This method takes a contact object, then saves it using intents.
     *
     * @param c Contact you wish to save
     * @return returns true if successful, false if not.
     **/
     public boolean saveAContact(Contact c)
    {
        Intent editIntent = new Intent(Intent.ACTION_EDIT);
        editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        editIntent.setDataAndType(Uri.parse(c.mSelectedContactUri), ContactsContract.Contacts.CONTENT_ITEM_TYPE)
        .putExtra(Contacts.Intents.Insert.PHONE, c.cellPhoneNumber).putExtra(Contacts.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        mContext.startActivity(editIntent);
        return true;
    }

    /**
     * This method fetches group members given a group ID. returns a list of Contacts.
     * @param groupId Id of group.
     * @return List of contacts in a group.
     * **/
    public List<String> fetchGroupMembers(String groupId){
        List<String> list = new ArrayList<String>();

        String where =  ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID +"="+groupId
                +" AND "
                + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE+"='"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE+"'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, ContactsContract.Data.DISPLAY_NAME};
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, where,null,
                ContactsContract.Data.DISPLAY_NAME+" COLLATE LOCALIZED ASC");
        while(cursor.moveToNext()){

           list.add( cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID))
            ); }
        cursor.close();
        for(String l: list)
        {
            Log.d("OOOOGGGGA",l);

        }

        return list;
    }


    /**
     * This method sorts alphabetically using Collections and a Comparator!!
     *
     * @param contacts List of contacts you want sorted
     * @return A contacts list sorted alphabetically by name!
     * **/
private List<Contact> sortingHat(List<Contact> contacts)
{
    if (contacts.size() > 0) {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(final Contact object1, final Contact object2) {
                return object1.displayName.compareTo(object2.displayName);
            }
        });
    }
    return contacts;
}

    /**
     * This method sorts groups alphabetically using Collectiosn and a Comparator!!
     *
     * @param groups List of groups you want sorted
     * @return A groups list sorted alphabetically by name!
     *
     * **/
    private List<Group> groupSort(List<Group> groups)
    {
        if(groups.size() > 0) {
            Collections.sort(groups, new Comparator<Group>() {
                @Override
                public int compare(Group object1, Group object2) {
                    return object1.name.compareTo(object2.name);
                }
            });
        }

        return groups;


    }


   /**
    * This method queries a database and returns a Photo object.
    *
    * @param cursor Cursor focused on contacts.
    * @return Photo Object to store information about a contact's photo
    *
    * **/
public Photo getContactPhoto(Cursor cursor)
{
    String photoID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
    String photoThumbNailURI = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
    String photoURI = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
    String photoFileId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_FILE_ID));
    return new Photo(photoID, photoURI, photoFileId, photoThumbNailURI);
}

    /**
     * Returns a list of contact names in String format.
     *
     * @return List of Contact Name strings. Generally used for gridview in an effort to conserve time and space.
     * **/
    public List<String> getContactNameList()
    {
        List<String> contactStrings = new ArrayList<String>();
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        while (cur.moveToNext()) {
            contactStrings.add(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        }
        return contactStrings;
    }
    /**
     * Gets a list of characters. This method takes an alphabet of characters A-Z and returns only
     * those that have a contact who's name starts with that character. This is used in gridview displays.
     *
     * @return List of characters.
     * **/
    public List<Character> getContactCharList()
    {
        return getStringsToCharacters(getContactNameList());
    }
    /**
     * @ return List of characters. Turns strings into characters. Used in gridview loading.
     * **/
    public List<Character> getStringsToCharacters(List<String> list)
    {
        List<Character> a = new ArrayList<Character>();
        for(String s: list)
        {
           Character tempChar = s.toLowerCase().toCharArray()[0];
           if (!a.contains(tempChar))
            {
               a.add(tempChar);
            }
        }
        return a;
    }



    /**
     * Takes contact information. Then updates it.
     *
     * @param name name of the contact
     * @param cell mobile phone number of contact
     * @param home home phone number of contact
     * @param work work phone number of contact
     * @param email work email address of contact
     * @param ContactId id of the contact which you want to update
     * @return true if contact is updated successfully<br/>
     *         false if contact is not updated <br/>
     *         false if phone number contains any characters(It should contain only digits)<br/>
     *         false if email Address is invalid <br/><br/>
     *
     *  You can pass any one among the 3 parameters to update a contact.Passing all three parameters as <b>null</b> will not update the contact
     *  <br/><br/><b>Note: </b>This method requires permission <b>android.permission.WRITE_CONTACTS</b><br/>
     */

    public boolean updateContact(String name, String cell,String home, String work, String email,String ContactId, Context context)
    {
        boolean success = true;
        String phnumexp = "^[0-9]*$";

        try
        {
            name = name.trim();
            email = email.trim();
            cell = cell.trim();
            home = home.trim();
            work = work.trim();

            if(name.equals("")&&cell.equals("")&&email.equals("")&&home.equals("")&&work.equals(""))
            {
                success = false;
            }
            else if((!cell.equals(""))&& (!match(cell,phnumexp)) )
            {
                success = false;
            }
            else if((!home.equals(""))&& (!match(home,phnumexp)) )
            {
                success = false;
            }
            else if((!work.equals(""))&& (!match(work,phnumexp)) )
            {
                success = false;
            }
            else if( (!email.equals("")) && (!isEmailValid(email)) )
            {
                success = false;
            }
            else
            {
                ContentResolver contentResolver  = context.getContentResolver();

                String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] emailParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
                String[] nameParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                String[] numberParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};

                ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<android.content.ContentProviderOperation>();

                if(!email.equals(""))
                {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where,emailParams)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                            .build());
                }

                if(!name.equals(""))
                {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where,nameParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                            .build());
                }

                if(!cell.equals(""))
                {
                    //Home1 Cell2 Work3
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 1)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, home)
                            .build());

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 2)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, cell)
                            .build());

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, 3)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, work)
                            .build());
                }
                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        return success;
    }



    // To get COntact Ids of all contact use the below method

    /**
     *
     * @return arraylist containing id's  of all contacts <br/>
     *         empty arraylist if no contacts exist <br/><br/>
     * <b>Note: </b>This method requires permission <b>android.permission.READ_CONTACTS</b>
     */

    public void photoPicker()
    {
      int SELECT_PICTURE = 1;

// ...

        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra
                (
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[] { takePhotoIntent }
                );
       mContext.startActivity(chooserIntent);
//       startActivityForResult(chooserIntent, SELECT_PICTURE);


    }


    /**
     * Checks for proper E-mail formatting
     *
     * @param email Email you want parsed.
     * @return True if email is accepted. False if email is rejected
     * **/
    private boolean isEmailValid(String email)
    {
        String emailAddress = email.toString().trim();
        if (emailAddress == null)
            return false;
        else if (emailAddress.equals(""))
            return false;
        else if (emailAddress.length() <= 6)
            return false;
        else {
            String expression = "^[a-z][a-z|0-9|]*([_][a-z|0-9]+)*([.][a-z|0-9]+([_][a-z|0-9]+)*)?@[a-z][a-z|0-9|]*\\.([a-z][a-z|0-9]*(\\.[a-z][a-z|0-9]*)?)$";
            CharSequence inputStr = emailAddress;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                return true;
            else
                return false;
        }
    }

    /**
     * Regex Matcher
     *
     * @param stringToCompare email
     * @param regularExpression regular expression used to parse email.
     * @return True if matched with regular expression.
     * @return False if not matched with regular expression.
     */
    private boolean match(String stringToCompare,String regularExpression)
    {
        boolean success = false;
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(stringToCompare);
        if(matcher.matches())
            success =true;
        return success;
    }



}
