package ai.brace.model;

public class TextInfo implements Comparable<TextInfo> {
    private Integer id;
    private String textdata;

    public TextInfo() {
    }

    public Integer getId() {
        return id;
    }

    public String getTextdata() {
        return textdata;
    }

    @Override
    public int compareTo(TextInfo o) {
        return Integer.compare(id, o.getId());
    }
}
