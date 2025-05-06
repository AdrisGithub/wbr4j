package io.github.adrisgithub.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

  @SuppressWarnings("unchecked")
  static <T> Result<? super T, RuntimeException> from(Supplier<? super T> supplier) {
    try {
      return (Result) Result.ofNullable((T) Objects.requireNonNull(supplier).get());
    } catch (RuntimeException exception) {
      return (Result<? super T, RuntimeException>) Result.of(exception);
    }
  }

  default boolean isError() {
    return ResultType.FAILURE.equals(getType());
  }

  default boolean isSuccess() {
    return ResultType.SUCCESS.equals(getType());
  }

  default void ifSuccess(Consumer<? super T> consumer) {
    this.getValue().ifPresent(Objects.requireNonNull(consumer));
  }

  default void ifFailure(Consumer<? super E> consumer) {
    this.getError().ifPresent(Objects.requireNonNull(consumer));
  }

  default Result<T, E> onSuccess(Consumer<? super T> consumer) {
    this.ifSuccess(Objects.requireNonNull(consumer));
    return this;
  }

  default Result<T, E> onSuccess(Runnable runnable) {
    this.ifSuccess(_ -> Objects.requireNonNull(runnable).run());
    return this;
  }

  default Result<T, E> onFailure(Consumer<? super E> consumer) {
    this.ifFailure(Objects.requireNonNull(consumer));
    return this;
  }

  default Result<T, E> onFailure(Runnable runnable) {
    this.ifFailure(_ -> Objects.requireNonNull(runnable).run());
    return this;
  }

  default T get() throws E {
    return getValue()
        .orElseThrow(() -> getError().orElseThrow());
  }

  default T orElse(T other) {
    return orElse(() -> other);
  }

  default E orElse(E exception) {
    return getError().orElse(exception);
  }

  default T orElse(Supplier<? extends T> supplier) {
    return getValue()
        .orElseGet(Objects.requireNonNull(supplier));
  }

  default Stream<T> stream() {
    return this.isSuccess() ? Stream.of(this.get()) : Stream.empty();
  }

}