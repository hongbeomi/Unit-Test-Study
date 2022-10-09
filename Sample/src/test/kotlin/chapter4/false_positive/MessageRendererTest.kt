package chapter4.false_positive

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class MessageRendererTest {

    @Test
    fun `하위 렌더링 클래스가_예상한 타입과 일치하고 올바른 순서로 나타내는지_검증`() {
        val sut = MessageRenderer()

        val renderers: List<IRenderer> = sut.subRenderers

        assertEquals(3, renderers.size)
        assert(renderers[0] is HeaderRenderer)
        assert(renderers[1] is BodyRenderer)
        assert(renderers[2] is FooterRenderer)
    }

    @Test
    fun `MessageRenderer 소스 코드를_읽고_검사`() {
        val sourceCode = File("./src/main/kotlin/chapter4/false_positive/MessageRenderer.kt")
            .useLines { it.toMutableList() }
            .drop(2)
            .joinToString("\n")

        assertEquals(
            "class MessageRenderer : IRenderer {" +
                    "\n\n" +
                    "    val subRenderers: List<IRenderer> = listOf(\n" +
                    "        HeaderRenderer(),\n" +
                    "        BodyRenderer(),\n" +
                    "        FooterRenderer(),\n" +
                    "    )" +
                    "\n\n" +
                    "    override fun render(message: Message): String = subRenderers.joinToString(separator = \"\")" +
                    " { it.render(message) }" +
                    "\n\n" +
                    "}",
            sourceCode
        )
    }

    @Test
    fun `안정된 버전의 하위 렌더링 클래스가_예상한 타입과 일치하고 올바른 순서로 나타내는지_검증`() {
        val sut = MessageRenderer()
        val message = Message("h", "b", "f")
        val html = sut.render(message)
        assertEquals("<h1>h</h1><b>b</b><i>f</i>", html)
    }

}