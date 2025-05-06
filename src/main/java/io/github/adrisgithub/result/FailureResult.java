package io.github.adrisgithub.result;

import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FailureResult<?, ?> that)) {
      return false;
    }
    return Objects.equals(getError(), that.getError());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(exception);
  }

  @Override
  public String toString() {
    return "Result[" +
        "exception=" + exception +
        ']';
  }
}
