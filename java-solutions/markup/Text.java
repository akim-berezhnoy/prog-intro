package markup;

public class Text implements Markdownable {

    final String value;

    public Text(String value) {
        this.value = value;
    }

    @Override
    public void toMarkdown(StringBuilder stringbuilder) { stringbuilder.append(value); }

    @Override
    public void toTex(StringBuilder stringBuilder) { stringBuilder.append(value); }

}
