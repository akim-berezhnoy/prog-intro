package markup;

import java.util.List;

public class Emphasis extends MarkableText {
    public Emphasis(List<Markdownable> list) {
        super(list);
        super.mark = "*";
        super.texMark = "emph";
    }
}
