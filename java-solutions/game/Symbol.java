package game;

public enum Symbol {
    BlANK ("."),
    CROSS ("X"),
    CIRCLE ("O"),
    DASH ("-"),
    VERT ("|"),
    ORANGUTAN ("\uD83E\uDDA7"),
    HEART ("❤️"),
    DIAMOND ("\uD83D\uDCA0️"),
    HAMBURGER ("\uD83C\uDF54");


    final String value;

    Symbol(String value) {
        this.value = value;
    }
}
