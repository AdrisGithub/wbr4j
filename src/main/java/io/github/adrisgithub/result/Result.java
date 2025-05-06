package io.github.adrisgithub.result;

import java.util.Objects;
import java.util.Optional;

public sealed interface Result<T, E extends RuntimeException> permits FailureResult, SuccessResult {

  Result<Void, NullPointerException> NULL_RESULT = Result.of(new NullPointerException());

  Optional<E> getError();

  Optional<T> getValue();

  ResultType getType();

  static <T, E extends RuntimeException> Result<T, E> of(T value) throws NullPointerException {
    return new SuccessResult<>(Objects.requireNonNull(value));
  }

  static <E extends RuntimeException> Result<Void, E> of(E exception) throws NullPointerException {
    return new FailureResult<>(Objects.requireNonNull(exception));
  }

  @SuppressWarnings("unchecked")
  static <T> Result<T, NullPointerException> ofNullable(T value) {
    return Optional.ofNullable(value)
        .map(Result::of)
        .orElse((Result) NULL_RESULT);
  }

}