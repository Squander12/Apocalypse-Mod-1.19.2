package me.squander.apocalypse.misc;

public enum BackpackType {
    POUCH("pouch", 9, 3, 3, 62, 17),
    SMALL_BACKPACK("small_backpack", 18, 9, 2, 8, 27),
    LARGE_BACKPACK("large_backpack", 27, 9, 3, 8, 18);

    public final String name;
    public final int size;
    public final int row, column;
    public final int xStart, yStart;

    BackpackType(String name, int size, int column, int row, int xStart, int yStart){
        this.name = name;
        this.size= size;
        this.row = row;
        this.column = column;
        this.xStart = xStart;
        this.yStart = yStart;
    }
}
