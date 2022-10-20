package markup;

public interface Markable {
    void toMarkdown(StringBuilder stringbuilder);

    void toTex(StringBuilder stringbuilder);
}
