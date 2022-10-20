package markup;

import java.util.List;

public class Strong extends AbstractText {
    public Strong(List<Markable> list) {
        super(list);
        super.mark = "__";
        super.texMark = "textbf";
    }
}
