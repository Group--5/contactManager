package otto.contacts.app;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.io.Serializable;

/**
 * This is a photo Pojo that stores profile picture information. It is also serializable.
 */
public class Photo implements Serializable{
    public String photoID;
    public String photoThumbNailURI;
    public String photoURI;
    public String photoFileId;

    /**This is a standard constructor for a profile photo object.
     * @param photoID Lookup Id for photo used for saving a photo
     * @param photoURI This is a photo URI that refferences the photo's location
     * @param photoFileId This is more file information on our photo.
     * @param photoThumbNailURI This is a URI for a smaller thumbnail size of our photo. This feature will be implmented in future productions of this app.
     * **/
    public Photo(String photoID, String photoURI, String photoFileId, String photoThumbNailURI)
    {
        this.photoID = photoID;
        this.photoURI = photoURI;
        this.photoFileId = photoFileId;
        this.photoThumbNailURI = photoThumbNailURI;
    }

    /** This method loads a Bitmap using information stored in this object. Expect errors if the object
     * is improperly instantiated.
     * @param context We need a context to make a corresponding bitmap object
     * @param id id
     * @return A bitmap to be displayed on a view of your choosing**/
    public static Bitmap loadContactPhotoBitMap(Context context, long  id) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            return null;
        }
        return BitmapFactory.decodeStream(input);
    }

}
