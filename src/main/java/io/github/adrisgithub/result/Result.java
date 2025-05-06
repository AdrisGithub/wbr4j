package io.github.adrisgithub.result;

import java.util.Optional;

public sealed interface Result<T, E extends RuntimeException> permits FailureResult, SuccessResult {

  Optional<E> getError();

  Optional<T> getValue();

  ResultType getType();
}