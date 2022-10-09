package chapter4.false_positive

import org.junit.jupiter.api.Test
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

}