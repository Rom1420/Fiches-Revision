package org.example;

import java.util.function.Function;

public record Option<T>(T value) {
    static <U> Option <U> empty(){
        return new Option<>(null);
    }

    static <U> Option <U> of(U value){
        return new Option<>(value);
    }

    T orElse(T other){
        return value == null ? other : value;
    }

    <U> Option <U> map(Function<T, U> f){
        return value == null ? empty() : of(f.apply(value));
    }
}
