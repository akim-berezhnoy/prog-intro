package markup;

public interface Markdownable {
    void toMarkdown(StringBuilder stringbuilder);

    void toTex(StringBuilder stringbuilder);
}
