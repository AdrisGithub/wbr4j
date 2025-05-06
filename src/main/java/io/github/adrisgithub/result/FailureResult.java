package io.github.adrisgithub.result;

import java.util.Optional;

public final class FailureResult<T, E extends RuntimeException> implements Result<T, E> {

  private final E exception;

  FailureResult(E exception) {
    this.exception = exception;
  }

  @Override
  public ResultType getType() {
    return ResultType.FAILURE;
  }

  @Override
  public Optional<E> getError() {
    return Optional.of(this.exception);
  }

  @Override
  public Optional<T> getValue() {
    return Optional.empty();
  }
}
