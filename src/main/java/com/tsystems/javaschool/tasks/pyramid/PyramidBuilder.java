package com.tsystems.javaschool.tasks.pyramid;

import java.util.Iterator;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        int rows = Rows(inputNumbers.size());

        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }

        int columns = Columns(rows);
        int[][] res = new int[rows][columns];

        inputNumbers.sort(Integer::compareTo);


        int index=0;

        for (int i = 0; i < rows; i++) {
            int row = i + 1;
            int padding = Padding(columns, row);
            for (int count = 0; count < row; count++) {
                res[i][padding] = inputNumbers.get(index);
                index++;
                padding += 2;
            }
        }
        return res;
    }



    private int Rows(int count) {
        double d = Math.sqrt(8 * count + 1);
        if (d != (int) d) { throw new CannotBuildPyramidException();}
        return ((int) d - 1) / 2;
    }

    private int Columns(int rowsCount) {
        return 2 * rowsCount - 1;
    }

    private int Padding(int columnsCount, int rowNumber) {
        return (columnsCount - (2 * rowNumber - 1)) / 2;
    }



}
