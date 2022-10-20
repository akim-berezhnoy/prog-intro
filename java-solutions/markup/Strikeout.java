package markup;

import java.util.List;

public class Strikeout extends MarkableText {
    public Strikeout(List<Markdownable> list) {
        super(list);
        super.mark = "~";
        super.texMark = "textst";
    }
}
