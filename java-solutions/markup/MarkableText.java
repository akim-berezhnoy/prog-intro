package Markdown.HT;

import java.util.List;

abstract class MarkableText extends AbstractText {

    protected String mark;

    protected MarkableText(List<Markdownable> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(mark);
        for (Markdownable textElement : super.list) {
            textElement.toMarkdown(stringBuilder);
        }
        stringBuilder.append(mark);
    }
}
