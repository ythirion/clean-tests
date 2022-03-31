using FluentAssertions;
using Xunit;

namespace CleanTests.Tests;

public class DemoTests
{
    [Fact]
    public void Should_Return_False_For_Abc()
    {
        Demo.IsLong("abc").Should().BeFalse();
    }
}