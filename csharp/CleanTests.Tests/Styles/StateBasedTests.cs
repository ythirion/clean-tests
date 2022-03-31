using System.Collections.Generic;
using FluentAssertions;
using LanguageExt;
using Xunit;

namespace CleanTests.Tests.Styles;

public class StateBasedTests
{
    [Fact]
    public void It_Should_Add_Given_Product_To_The_Order()
    {
        var product = new Product("Free Guy");
        var sut = new Order();

        sut.Add(product);

        sut.Products
            .Should()
            .HaveCount(1)
            .And
            .Satisfy(item => item.Equals(product));
    }

    private sealed record Product(string Name);

    private class Order
    {
        private readonly List<Product> _products = new();

        public Seq<Product> Products => _products.ToSeq();

        public void Add(Product product)
        {
            _products.Add(product);
        }
    }

    /*    class Order {
        private final List<Product> products = new ArrayList<>();

        List<Product> getProducts() {
            return Collections.unmodifiableList(products);
        }

        void add(Product product) {
            products.add(product);
        }
    }*/

    /*
     * class StateBasedTests {
    @Test
    void it_should_add_given_product_to_the_order() {
        val product = new Product("Free Guy");
        val sut = new Order();

        sut.add(product);

        // Verify the state
        assertThat(sut.getProducts())
                .hasSize(1)
                .allMatch(item -> item.equals(product));
    }

    @AllArgsConstructor
    class Product {
        private final String name;
    }

    class Order {
        private final List<Product> products = new ArrayList<>();

        List<Product> getProducts() {
            return Collections.unmodifiableList(products);
        }

        void add(Product product) {
            products.add(product);
        }
    }
}
     */
}