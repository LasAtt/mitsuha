package io.serd.mitsuha.util

import java.io.File
import java.nio.file.Files

fun File.readBytes(): ByteArray = Files.readAllBytes(this.toPath())
