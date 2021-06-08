package com.epam.esm.service.parser;

import com.epam.esm.service.service.parser.SearchParamsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class SearchParamsParserTest {
    SearchParamsParser searchParamsParser = new SearchParamsParser();

    static Stream<Arguments> testDataTags() {
        return Stream.of(
                Arguments.of("tag1,tag2", new ArrayList<>(Arrays.asList("tag1", "tag2"))),
                Arguments.of("tag1,tag2,tag3", new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3"))),
                Arguments.of(null, new ArrayList<>())
        );
    }

    @ParameterizedTest
    @MethodSource("testDataTags")
    void parseTagNamesTest(String tagsString, List<String> expected) {
        List<String> actual = searchParamsParser.parseTagNames(tagsString);
        Assertions.assertEquals(actual, expected);
    }
}
