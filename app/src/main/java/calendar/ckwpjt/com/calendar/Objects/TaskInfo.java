package calendar.ckwpjt.com.calendar.Objects;

public class TaskInfo {
    private String mTitle;
    private String mContents;

    public TaskInfo(String title, String contents){
        this.mTitle = title;
        this.mContents = contents;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContents() {
        return mContents;
    }
}
