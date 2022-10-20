package markup;

import java.util.List;

public class Strikeout extends AbstractText {
    public Strikeout(List<Markable> list) {
        super(list);
        super.mark = "~";
        super.texMark = "textst";
    }
}
