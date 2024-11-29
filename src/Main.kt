import engine.SpriteLoader
import screens.MenuPanel
import java.awt.*
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        val frame = GameFrame()
        frame.isVisible = true
    }
}

class GameFrame : JFrame() {
    init {
        title = "СЫРНОЕ БЕЗУМИЕ"
        iconImage = SpriteLoader.load("resources/sprites/cheese.png")
        size = Dimension(1280, 1000)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false
        contentPane = MenuPanel(this)
    }
}
