/**
 * Created by IntelliJ IDEA.
 * Author:Royal Aliyev
 * Date:2019-11-05
 * Time:00:04
 */

package com.elecdrones;

import java.util.ArrayList;

class LinearAlgebra {
    /**
     * Constructor with 0 Argument
     */
    public LinearAlgebra() {
    }

    /**
     * MultiplyMatrices Method Starts, Multiply Two Matrices with Different Dimensions
     */
    public ArrayList<ArrayList<Double>> multiplyMatrices(ArrayList<ArrayList<Double>> firstMatrixArraylist, ArrayList<ArrayList<Double>> secondMatrixArraylist) {
        ArrayList<ArrayList<Double>> multiplicationMatrixArrayList = new ArrayList<>();
        ArrayList<Double> multiplicationVectorArrayList = new ArrayList<>();

        int theNumberOfRowsInFirstMatrixArraylist = 0;
        int theNumberOfColumnsInFirstMatrixArraylist = 0;
        int theNumberOfRowsInSecondMatrixArraylist = 0;
        int theNumberOfColumnsInSecondMatrixArraylist = 0;

        for (int counter = 0; counter < firstMatrixArraylist.size(); counter = counter + 1)
            theNumberOfRowsInFirstMatrixArraylist = theNumberOfRowsInFirstMatrixArraylist + 1;

        for (int counter = 0; counter < firstMatrixArraylist.get(1).size(); counter = counter + 1)
            theNumberOfColumnsInFirstMatrixArraylist = theNumberOfColumnsInFirstMatrixArraylist + 1;

        for (int counter = 0; counter < secondMatrixArraylist.size(); counter = counter + 1)
            theNumberOfRowsInSecondMatrixArraylist = theNumberOfRowsInSecondMatrixArraylist + 1;

        for (int counter = 0; counter < secondMatrixArraylist.get(1).size(); counter = counter + 1)
            theNumberOfColumnsInSecondMatrixArraylist = theNumberOfColumnsInSecondMatrixArraylist + 1;

        if (theNumberOfColumnsInFirstMatrixArraylist != theNumberOfRowsInSecondMatrixArraylist) {
            System.out.println("Make sure that the number of columns in the 1st matrix equals the number of rows in the 2nd matrix.");
            return null;
        } else {
            double[][] multiplicationMatrixArray = new double[theNumberOfRowsInFirstMatrixArraylist][theNumberOfColumnsInSecondMatrixArraylist];

            for (int firstCounter = 0; firstCounter < theNumberOfRowsInFirstMatrixArraylist; ++firstCounter) {
                for (int secondCounter = 0; secondCounter < theNumberOfColumnsInSecondMatrixArraylist; ++secondCounter) {
                    multiplicationMatrixArray[firstCounter][secondCounter] = 0;
                    for (int thirdCounter = 0; thirdCounter < theNumberOfColumnsInFirstMatrixArraylist; ++thirdCounter) {
                        multiplicationMatrixArray[firstCounter][secondCounter] += firstMatrixArraylist.get(firstCounter).get(thirdCounter) * secondMatrixArraylist.get(thirdCounter).get(secondCounter);
                    }
                }
            }

            for (int firstCounter = 0; firstCounter < theNumberOfRowsInFirstMatrixArraylist; ++firstCounter) {
                for (int secondCounter = 0; secondCounter < theNumberOfColumnsInSecondMatrixArraylist; ++secondCounter) {
                    multiplicationVectorArrayList.add(multiplicationMatrixArray[firstCounter][secondCounter]);
                }
                multiplicationMatrixArrayList.add(multiplicationVectorArrayList);
                multiplicationVectorArrayList = new ArrayList<>();
            }
            return multiplicationMatrixArrayList;
        }
    }

    /**
     * FindMod26Matrix Method Starts, Finds All Mod 26 of All Elements of Matrix
     */
    public ArrayList<ArrayList<Double>> findRemainder(ArrayList<ArrayList<Double>> matrixArrayList, double divider) {
        for (int firstCounter = 0; firstCounter < matrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < matrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                if (matrixArrayList.get(firstCounter).get(secondCounter) % divider < 0) {
                    matrixArrayList.get(firstCounter).set(secondCounter, matrixArrayList.get(firstCounter).get(secondCounter) % divider + divider);
                } else {
                    matrixArrayList.get(firstCounter).set(secondCounter, matrixArrayList.get(firstCounter).get(secondCounter) % divider);
                }
            }
        }
        return matrixArrayList;
    }

    /**
     * Inverts Square Matrix
     */
    public ArrayList<ArrayList<Double>> invertMatrix(ArrayList<ArrayList<Double>> matrixArrayList) {
        ArrayList<ArrayList<Double>> invertedMatrixArrayList = new ArrayList<>();
        ArrayList<Double> invertedVectorArrayList = new ArrayList<>();
        double[][] matrixArray = new double[matrixArrayList.size()][matrixArrayList.size()];

        for (int firstCounter = 0; firstCounter < matrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < matrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                matrixArray[firstCounter][secondCounter] = matrixArrayList.get(firstCounter).get(secondCounter);
            }
        }

        int matrixArrayListDimensions = matrixArray.length;
        double[][] backwardSubstitutionsMatrix = new double[matrixArrayListDimensions][matrixArrayListDimensions];
        double[][] ratiosMatrix = new double[matrixArrayListDimensions][matrixArrayListDimensions];
        int[] indexOfMatrix = new int[matrixArrayListDimensions];

        for (int counter = 0; counter < matrixArrayListDimensions; ++counter) {
            ratiosMatrix[counter][counter] = 1;
        }

        findGaussJordanElimination(matrixArray, indexOfMatrix);

        for (int firstCounter = 0; firstCounter < matrixArrayListDimensions - 1; ++firstCounter)
            for (int secondCounter = firstCounter + 1; secondCounter < matrixArrayListDimensions; ++secondCounter)
                for (int thirdCounter = 0; thirdCounter < matrixArrayListDimensions; ++thirdCounter)
                    ratiosMatrix[indexOfMatrix[secondCounter]][thirdCounter]
                            -= matrixArray[indexOfMatrix[secondCounter]][firstCounter] * ratiosMatrix[indexOfMatrix[firstCounter]][thirdCounter];

        for (int firstCounter = 0; firstCounter < matrixArrayListDimensions; ++firstCounter) {
            backwardSubstitutionsMatrix[matrixArrayListDimensions - 1][firstCounter] = ratiosMatrix[indexOfMatrix[matrixArrayListDimensions - 1]][firstCounter] / matrixArray[indexOfMatrix[matrixArrayListDimensions - 1]][matrixArrayListDimensions - 1];
            for (int secondCounter = matrixArrayListDimensions - 2; secondCounter >= 0; --secondCounter) {
                backwardSubstitutionsMatrix[secondCounter][firstCounter] = ratiosMatrix[indexOfMatrix[secondCounter]][firstCounter];
                for (int thirdCounter = secondCounter + 1; thirdCounter < matrixArrayListDimensions; ++thirdCounter) {
                    backwardSubstitutionsMatrix[secondCounter][firstCounter] -= matrixArray[indexOfMatrix[secondCounter]][thirdCounter] * backwardSubstitutionsMatrix[thirdCounter][firstCounter];
                }
                backwardSubstitutionsMatrix[secondCounter][firstCounter] /= matrixArray[indexOfMatrix[secondCounter]][secondCounter];
            }
        }

        for (int firstCounter = 0; firstCounter < backwardSubstitutionsMatrix.length; firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < backwardSubstitutionsMatrix[firstCounter].length; secondCounter = secondCounter + 1) {
                invertedVectorArrayList.add(backwardSubstitutionsMatrix[firstCounter][secondCounter]);
            }
            invertedMatrixArrayList.add(invertedVectorArrayList);
            invertedVectorArrayList = new ArrayList<>();
        }

        return invertedMatrixArrayList;
    }

    /**
     * Find Gauss Elemination of Matrix to Calculate Inverse of Matrix
     */
    private static void findGaussJordanElimination(double[][] matrixArray, int[] indexOfElementArray) {
        int indexOfElementLength = indexOfElementArray.length;
        double[] indexOfElementLengthArray = new double[indexOfElementLength];

        for (int counter = 0; counter < indexOfElementLength; ++counter) {
            indexOfElementArray[counter] = counter;
        }

        for (int firstCounter = 0; firstCounter < indexOfElementLength; ++firstCounter) {
            double secondRescalingFactor = 0;
            for (int secondCounter = 0; secondCounter < indexOfElementLength; ++secondCounter) {
                double firstRescalingFactor = Math.abs(matrixArray[firstCounter][secondCounter]);
                if (firstRescalingFactor > secondRescalingFactor) {
                    secondRescalingFactor = firstRescalingFactor;
                }
            }
            indexOfElementLengthArray[firstCounter] = secondRescalingFactor;
        }

        int pivotingElement = 0;
        for (int firstCounter = 0; firstCounter < indexOfElementLength - 1; ++firstCounter) {
            double secondPivotingElement = 0;
            for (int secondCounter = firstCounter; secondCounter < indexOfElementLength; ++secondCounter) {
                double firstPivotingElement = Math.abs(matrixArray[indexOfElementArray[secondCounter]][firstCounter]);
                firstPivotingElement /= indexOfElementLengthArray[indexOfElementArray[secondCounter]];
                if (firstPivotingElement > secondPivotingElement) {
                    secondPivotingElement = firstPivotingElement;
                    pivotingElement = secondCounter;
                }
            }

            int pivotingOrder = indexOfElementArray[firstCounter];
            indexOfElementArray[firstCounter] = indexOfElementArray[pivotingElement];
            indexOfElementArray[pivotingElement] = pivotingOrder;
            for (int thirdCounter = firstCounter + 1; thirdCounter < indexOfElementLength; ++thirdCounter) {
                double thirdCounterTHElement = matrixArray[indexOfElementArray[thirdCounter]][firstCounter] / matrixArray[indexOfElementArray[firstCounter]][firstCounter];
                matrixArray[indexOfElementArray[thirdCounter]][firstCounter] = thirdCounterTHElement;
                for (int fourthCounter = firstCounter + 1; fourthCounter < indexOfElementLength; ++fourthCounter) {
                    matrixArray[indexOfElementArray[thirdCounter]][fourthCounter] -= thirdCounterTHElement * matrixArray[indexOfElementArray[firstCounter]][fourthCounter];
                }
            }
        }
    }
}