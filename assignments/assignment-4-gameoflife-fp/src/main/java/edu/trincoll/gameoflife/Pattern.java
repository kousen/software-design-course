package edu.trincoll.gameoflife;

public enum Pattern {
    BLOCK("""
        **
        **
        """),

    BEEHIVE("""
        .**..
        *..*.
        .**..
        """),

    LOAF("""
        .**..
        *..*
        .*.*
        ..*..
        """),

    BOAT("""
        **.
        *.*
        .*.
        """),

    BLINKER("""
        ...
        ***
        ...
        """),

    TOAD("""
        .***
        ***
        """),

    BEACON("""
        **..
        **..
        ..**
        ..**
        """),

    PULSAR("""
        ..***...***..
        .............
        *....*.*.....
        *....*.*.....
        *....*.*.....
        ..***...***..
        .............
        ..***...***..
        *....*.*.....
        *....*.*.....
        *....*.*.....
        .............
        ..***...***..
        """),

    GLIDER("""
        .*.
        ..*
        ***
        """),

    LIGHTWEIGHT_SPACESHIP("""
        .*..*
        *....
        *...*
        ****
        """);

    private final String pattern;

    Pattern(String pattern) {
        this.pattern = pattern;
    }

    public Grid toGrid() {
        return new Grid(pattern);
    }

    public String getPattern() {
        return pattern;
    }
}