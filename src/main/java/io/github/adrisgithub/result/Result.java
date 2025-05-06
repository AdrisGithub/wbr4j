package io.github.adrisgithub.result;

import java.util.Objects;
import java.util.Optional;

public sealed interface Result<T, E extends RuntimeException> permits FailureResult, SuccessResult {

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
    if (Objects.isNull(value)) {
      return (Result<T, NullPointerException>) Result.of(new NullPointerException());
    }
    return Result.of(value);
  }

  @SuppressWarnings("unchecked")
  static <E extends RuntimeException> Result<Void, E> ofNullable(E exception) {
    if (Objects.isNull(exception)) {
      return (Result<Void, E>) Result.of(new NullPointerException());
    }
    return new FailureResult<>(exception);
  }

}