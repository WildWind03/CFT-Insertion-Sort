package com.chirikhin.cft.sorter;

import com.chirikhin.cft.algorithm.ISorter;
import com.chirikhin.cft.algorithm.SortAlgorithm;
import com.chirikhin.cft.algorithm.SorterFactory;
import com.chirikhin.cft.argumentparser.ArgumentParser;
import com.chirikhin.cft.argumentparser.SortConfiguration;
import com.chirikhin.cft.util.IOUtil;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentParser argumentParser = new ArgumentParser(args);
            SortConfiguration sortConfiguration = argumentParser.parseConfiguration();

            String[] strings = IOUtil.getContent(sortConfiguration.getInFilename());
            ISorter iSorter = SorterFactory
                    .getInstance()
                    .createSorter(SortAlgorithm.INSERTION);

            switch (sortConfiguration.getSourceType()) {
                case INTEGER:
                    Integer[] integers = Arrays
                            .stream(strings)
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .toArray(Integer[]::new);

                    iSorter.sort(integers, sortConfiguration.getSortType());
                    IOUtil.write(sortConfiguration.getOutFilename(), integers);
                    break;

                case STRING:
                    iSorter.sort(strings, sortConfiguration.getSortType());
                    IOUtil.write(sortConfiguration.getOutFilename(), strings);
                    break;

                default:
                    throw new IllegalArgumentException("Inappropriate source type "
                            + sortConfiguration.getSourceType().name());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}