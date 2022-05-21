package com.kivanval.telegram.data.repositories;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class UserIdArguments {
    static Stream<Arguments> provideUserIdForRepository() {
        return LongStream.rangeClosed(1, 100)
                .mapToObj(Arguments::of);
    }
}
