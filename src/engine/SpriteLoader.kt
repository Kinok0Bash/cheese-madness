package engine

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

object SpriteLoader {
    fun load(name: String): BufferedImage {
        return ImageIO.read(File("resources/sprites/$name.png"))
    }
}

