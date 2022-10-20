package markup;

import java.util.List;

public class Paragraph implements Markable {

    private final List<Markable> paragraph;

    public Paragraph(List<Markable> paragraph) {
        this.paragraph = paragraph;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (Markable textElement : paragraph) {
            textElement.toMarkdown(stringBuilder);
        }
    }

    @Override
    public void toTex(StringBuilder stringBuilder) {
        for (Markable textElement : paragraph) {
            textElement.toTex(stringBuilder);
        }
    }

}
