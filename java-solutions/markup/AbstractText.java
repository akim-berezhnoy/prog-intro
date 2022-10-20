package markup;

import java.util.List;

abstract class AbstractText implements Markable {

    protected String mark;

    protected String texMark;

    protected List<Markable> list;

    protected AbstractText(List<Markable> list) { this.list = list ;}

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(mark);
        for (Markable textElement : list) {
            textElement.toMarkdown(stringBuilder);
        }
        stringBuilder.append(mark);
    }

    @Override
    public void toTex(StringBuilder stringBuilder) {
        stringBuilder.append("\\").append(texMark).append("{");
        for (Markable textElement : list) {
            textElement.toTex(stringBuilder);
        }
        stringBuilder.append("}");
    }
}
