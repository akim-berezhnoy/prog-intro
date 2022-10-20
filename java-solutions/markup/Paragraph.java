package Markdown.HT;

import java.util.List;

public class Paragraph implements Markdownable {

    private final List<Markdownable> paragraph;

    public Paragraph(List<Markdownable> paragraph) {
        this.paragraph = paragraph;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (Markdownable textElement : paragraph) {
            textElement.toMarkdown(stringBuilder);
        }
    }

}
