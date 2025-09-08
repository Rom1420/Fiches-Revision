package org.example;

public record Lst<T>(T car, Lst<T> cdr) {

}
