package markup;

import java.util.List;

abstract class MarkableText extends AbstractText {

    protected String mark;

    protected String texMark;

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

    @Override
    public void toTex(StringBuilder stringBuilder) {
        stringBuilder.append("\\").append(texMark).append("{");
        for (Markdownable textElement : super.list) {
            textElement.toTex(stringBuilder);
        }
        stringBuilder.append("}");
    }
}
