package io.github.adrisgithub.result;

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

}
