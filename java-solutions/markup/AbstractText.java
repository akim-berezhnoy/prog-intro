package Markdown.HT;

import java.util.List;

abstract class AbstractText implements Markdownable {

    protected List<Markdownable> list;

    protected AbstractText(List<Markdownable> list) {
        this.list = list;
    }

}
