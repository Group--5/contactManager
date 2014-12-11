package otto.contacts.app;

/**
 * This interface is used for type-control. All ui elements extend this interface in an almost composite fashion
 */
public interface ListHeaderInterface {
    public boolean isSectionHeader();
    public boolean searchFor();
    public boolean isContact();
    public boolean isGroup();

    public Contact getContact();
    public SectionItem getSectionHeader();
    public Group getGroup();
}
