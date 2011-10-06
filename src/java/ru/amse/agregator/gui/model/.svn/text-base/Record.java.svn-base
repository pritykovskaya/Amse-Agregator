package ru.amse.agregator.gui.model;

import java.util.ArrayList;
import java.util.List;

public class Record {
    private final List<Cell> cells = new ArrayList<Cell>();

    public void addCell(final Cell cell) {
        cells.add(cell);
    }

    public void addCell(final String name, final Object value) {
        addCell(new Cell(name, value));
    }

    public List<Cell> getCells() {
        return cells;
    }
}
