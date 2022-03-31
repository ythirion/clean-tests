using CleanTests.Customer;
using FluentAssertions;
using LanguageExt;
using Xunit;

namespace CleanTests.Tests.AntiPatterns.Solution;

public class PriceEngineTests
{
    [Fact]
    public void Discount_Of_3_Products_Should_Be_3_Percent()
    {
        var products = Seq.create(new Product("P1"), new Product("P2"), new Product("P3"));
        var discount = PriceEngine.CalculateDiscount(products.ToArray());

        discount.Should().Be(0.03);
    }
}