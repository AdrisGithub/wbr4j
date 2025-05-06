package io.github.adrisgithub.result;

public sealed interface Result<T, E extends RuntimeException> permits FailureResult, SuccessResult {

}