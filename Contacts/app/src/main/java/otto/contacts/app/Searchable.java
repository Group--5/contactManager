package otto.contacts.app;

import java.io.Serializable;

/**
 * Created by study on 10/9/14.
 */
public class Searchable implements Serializable {

    private String displayName;
    private int photoRefference;
    private int generalRefference;

    public Searchable(String displayName, int photoRefference, int generalRefference)
    {
        this.displayName = displayName;
        this.photoRefference = photoRefference;
        this.generalRefference = generalRefference;
    }

    public Searchable(String displayName, int generalRefference)
    {
        this.displayName = displayName;
        this.generalRefference = generalRefference;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public int getPhotoRefference()
    {
        return this.photoRefference;
    }

    public int getGeneralRefference()
    {
        return this.generalRefference;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public void setPhotoRefference(int photoRefference)
    {
        this.photoRefference = photoRefference;
    }

    public void setGeneralRefference(int generalRefference)
    {
        this.generalRefference = generalRefference;
    }
}
