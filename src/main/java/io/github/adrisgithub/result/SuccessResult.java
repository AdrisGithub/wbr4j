package io.github.adrisgithub.result;

import java.util.Objects;
import java.util.Optional;

public final class SuccessResult<T, E extends RuntimeException> implements Result<T, E> {

  private final T value;

  SuccessResult(T value) {
    super();
    this.value = value;
  }

  @Override
  public ResultType getType() {
    return ResultType.SUCCESS;
  }

  @Override
  public Optional<E> getError() {
    return Optional.empty();
  }

  @Override
  public Optional<T> getValue() {
    return Optional.of(value);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SuccessResult<?, ?> that)) {
      return false;
    }
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "Result[" +
        "value=" + value +
        ']';
  }

}
