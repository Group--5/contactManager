package otto.contacts.app;

/**
 * Created by study on 10/10/14.
 */
public class Contact extends Searchable {
    private String firstName;
    private String lastName;
    public Contact(String firstName, String lastName, int pictureRefID, int generalReffId)
    {
        super(firstName + lastName,pictureRefID,generalReffId);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Contact(String firstName, String lastName, int generalReffId)
    {
        super(firstName + " " + lastName,generalReffId);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
