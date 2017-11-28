package io.serd.mitsuha.buffered_image

import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage


fun BufferedImage.scaleTo64Bit(): BufferedImage = Thumbnails.of(this)
    .forceSize(8, 8)
    .asBufferedImage()

fun BufferedImage.toGrayScale(): BufferedImage {
    val image = BufferedImage(width, height,
        BufferedImage.TYPE_BYTE_GRAY)
    val g = image.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return image
}

fun BufferedImage.getGrayScaleRBG(x: Int, y: Int) = getRGB(x, y) and 0xFF

fun computeHash(bufferedImage: BufferedImage): Long {
    val image = bufferedImage.scaleTo64Bit().toGrayScale()
    val mean = image.computeMeanColor()
    return computeHash(image, mean)
}

fun BufferedImage.computeMeanColor(): Int {
    return (0 until height).flatMap { x -> (0 until width).map { y ->
        getGrayScaleRBG(x, y)
    } }.reduce { acc, color -> acc + color }.let {
        it / (width * height)
    }
}

private fun computeHash(bufferedImage: BufferedImage, mean: Int): Long {
    return (0 until bufferedImage.height).flatMap { x -> (0 until bufferedImage.width).map { y ->
        if (bufferedImage.getGrayScaleRBG(x, y) < mean) 1L else 0L
    } }.reduce { acc, bit ->
        (acc shl 1) + bit
    }
}

