package org.example.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomCellsRenderer extends DefaultTableCellRenderer {

    private final List<PaintingRange> highlightedRanges = new ArrayList<>();

    public CustomCellsRenderer(int row, int startColumn, int endColumn) {
        addRange(row, startColumn, endColumn);
    }

    public void addRange(int row, int startColumn, int endColumn) {
        highlightedRanges.add(new PaintingRange(row, startColumn, endColumn));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isPaintedCell(row, column)) {
            cellComponent.setBackground(Color.LIGHT_GRAY);
        } else {
            cellComponent.setBackground(table.getBackground());
        }
        return cellComponent;
    }

    private boolean isPaintedCell(int row, int column) {
        for (PaintingRange range : highlightedRanges) {
            if (range.isInCell(row, column)) {
                return true;
            }
        }
        return false;
    }

    private static class PaintingRange {
        private final int row;
        private final int startColumn;
        private final int endColumn;

        public PaintingRange(int row, int startColumn, int endColumn) {
            this.row = row;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
        }

        public boolean isInCell(int row, int column) {
            return this.row == row && column >= startColumn && column <= endColumn;
        }
    }
}

