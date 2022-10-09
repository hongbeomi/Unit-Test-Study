package chapter4.false_positive

class HeaderRenderer: IRenderer {

    override fun render(message: Message): String = "<h>${message.header}</h>"

}