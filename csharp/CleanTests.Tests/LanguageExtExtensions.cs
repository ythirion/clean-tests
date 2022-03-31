using LanguageExt;

namespace CleanTests.Tests;

public static class LanguageExtExtensions
{
    public static TLeft LeftUnsafe<TLeft, TRight>(this Either<TLeft, TRight> either)
        => either.LeftToSeq().Single();

    public static TRight RightUnsafe<TLeft, TRight>(this Either<TLeft, TRight> either)
        => either.RightToSeq().Single();
}