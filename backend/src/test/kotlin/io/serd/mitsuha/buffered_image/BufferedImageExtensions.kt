package io.serd.mitsuha.buffered_image

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import javax.imageio.ImageIO

class BufferedImageExtensionsTest {

    @Test
    fun scaledImageGetsTheSameHash() {
        val original = ImageIO.read(File("src/test/resources/stock.jpg"))
        val scaled = ImageIO.read(File("src/test/resources/stock-scaled.jpg"))

        assertEquals(computeHash(original), computeHash(scaled))
    }
}
