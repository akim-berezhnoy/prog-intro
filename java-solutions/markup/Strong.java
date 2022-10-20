package Markdown.HT;

import java.util.List;

public class Strong extends MarkableText {
    public Strong(List<Markdownable> list) {
        super(list);
        super.mark = "__";
    }
}
