package com.bangkit.edims.presentation.components.faq

data class Faq(
    val question: String,
    val answer: String,
)

object FaqData {
    val faqList = listOf(
        Faq(
            "Question 1",
            "Answer 1"
        ),
        Faq(
            "Question 2",
            "Answer 2"
        ),
        Faq(
            "Question 3",
            "Answer 3"
        ),
        Faq(
            "Question 4",
            "Answer 4"
        ),
        Faq(
            "Question 5",
            "Answer 5"
        ),
    )
}