package chapter4.false_positive

class MessageRenderer : IRenderer {

    val subRenderers: List<IRenderer> = listOf(
        HeaderRenderer(),
        BodyRenderer(),
        FooterRenderer(),
    )

    override fun render(message: Message): String = subRenderers.joinToString(separator = "") { it.render(message) }

}