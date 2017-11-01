package io.serd.mitsuha.util

import io.serd.mitsuha.dao.Image
import java.io.File
import java.nio.file.Files

fun File.readBytes(): ByteArray = Files.readAllBytes(this.toPath())
