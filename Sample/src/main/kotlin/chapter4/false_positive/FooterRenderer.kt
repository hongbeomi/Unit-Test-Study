package chapter4.false_positive

class FooterRenderer: IRenderer {

    override fun render(message: Message): String = "<f>${message.footer}</f>"

}