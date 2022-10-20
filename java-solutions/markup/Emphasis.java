package markup;

import java.util.List;

public class Emphasis extends AbstractText {
    public Emphasis(List<Markable> list) {
        super(list);
        super.mark = "*";
        super.texMark = "emph";
    }
}
