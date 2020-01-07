package ai.brace.model;

public class BookInfo {
    private String version;
    private String uuid;
    private String lastModified;
    private String title;
    private String author;
    private String translator;
    private String releaseDate;
    private String language;
    private TextInfo[] textArray;

    public BookInfo() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(final String translator) {
        this.translator = translator;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public TextInfo[] getTextArray() {
        return textArray;
    }

    public void setTextArray(final TextInfo[] textArray) {
        this.textArray = textArray;
    }
}