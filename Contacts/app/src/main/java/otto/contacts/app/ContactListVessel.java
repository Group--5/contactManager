package otto.contacts.app;

import java.io.Serializable;
import java.util.List;

/**
*This is an object that is used for the transport of information across intents. It is used to
 * transport a list of contacts specifically. The use of this object is fairly straightforward.
 * Create==> Pack with List of contacts ==> put into intent's serializable object hashtable ==
 * ==> switch contexts ===> get intent ==> luck up vessel ==> deserialize ==> unpack contact list.
 *  */
public class ContactListVessel implements Serializable {
    List<Contact> mContacts;
    /**
     * Constructor takes contacts, stores them. This object is used to serialize lists.
     * @param contacts List of contacts you want to pack in vessel
     * **/
    public ContactListVessel(List<Contact> contacts)
    {
        this.mContacts = contacts;
    }

    /** Getter method used when you want to extract LIst of contacts from serializable vessel
     * @return List of contacts!
     * **/
    public List<Contact> getContacts()
    {
        return mContacts;
    }

}
