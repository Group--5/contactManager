package otto.contacts.app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by study on 10/5/14.
 */
public class ContactIntermidiary {
    private Context mContext;

    public ContactIntermidiary(Context context)
    {
        mContext = context;
    }


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
    public List<Character> getContactCharList()
    {
        return getStringsToCharacters(getContactNameList());
    }
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

    public List<Contact> getListOfContacts()
    {
    List<Contact> contacts = new ArrayList<Contact>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        while (cursor.moveToNext()) {


            String contactId = cursor.getString(cursor.getColumnIndex(
             ContactsContract.Contacts._ID));

            //May want to get basic info here like name, phone
            //Example:
            //Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null);
            //while (phones.moveToNext()) {
            //    String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
            //    Log.i("phone", phoneNumber);
            //}
            //phones.close();



//            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
//            while (emails.moveToNext()) {
//                String emailAddress = emails.getString(
//                        emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//
//                Log.i("emails", emailAddress);
//            }
//            emails.close();
        }
        cursor.close();


    return null;
    }


}
