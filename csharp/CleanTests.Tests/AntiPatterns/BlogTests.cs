using CleanTests.Blog;
using FluentAssertions;
using Xunit;

namespace CleanTests.Tests.AntiPatterns;

public class BlogTests
{
    [Fact]
    public void It_Should_Return_a_Right_For_Valid_Comment()
    {
        var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        var result = article.AddComment("Amazing article !!!", "Pablo Escobar");

        result.IsRight.Should().BeTrue();
    }

    [Fact]
    public void It_Should_Add_A_Comment_With_The_Given_Text()
    {
        var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        const string text = "Amazing article !!!";
        var result = article.AddComment(text, "Pablo Escobar");

        result.RightUnsafe()
            .Comments.Should()
            .HaveCount(1)
            .And
            .Satisfy(comment => comment.Text == text);
    }

    [Fact]
    public void It_Should_Add_A_Comment_With_The_Given_Author()
    {
        var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        const string author = "Pablo Escobar";
        var result = article.AddComment("Amazing article !!!", author);

        result.RightUnsafe()
            .Comments.Should()
            .HaveCount(1)
            .And
            .Satisfy(comment => comment.Author == author);
    }

    [Fact]
    public void It_Should_Add_A_Comment_With_The_Date_Of_The_Day()
    {
        var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        var result = article.AddComment("Amazing article !!!", "Pablo Escobar");
    }

    [Fact]
    public void It_Should_Add_A_Left_When_Adding_Existing_Comment()
    {
        var article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        var result = article.AddComment("Amazing article !!!", "Pablo Escobar")
            .Map(a => a.AddComment("Amazing article !!!", "Pablo Escobar"))
            .Flatten();

        result.IsLeft.Should().BeTrue();
    }
}