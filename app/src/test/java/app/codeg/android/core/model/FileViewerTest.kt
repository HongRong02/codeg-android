package app.codeg.android.core.model

import org.junit.Assert.*
import org.junit.Test

class FileViewerTest {
    @Test fun recognizesImageExtensionsCaseInsensitively() {
        assertTrue(isPreviewImage("folder/photo.JPEG"))
        assertTrue(isPreviewImage("a.webp"))
        assertFalse(isPreviewImage("file.png.txt"))
        assertFalse(isPreviewImage("LICENSE"))
    }

    @Test fun binaryDataIsNotRenderedAsText() {
        assertEquals(FileViewerContent.Unavailable(3), previewText("a\u0000b", "binary"))
    }

    @Test fun largeUtf8TextUsesByteLimit() {
        val text = "中".repeat(700_000)
        assertEquals(FileViewerContent.Unavailable(2_100_000, true), previewText(text, "large.txt"))
    }

    @Test fun textAndEmptyFilesRemainText() {
        assertEquals(FileViewerContent.Text("", "empty"), previewText("", "empty"))
        assertEquals(FileViewerContent.Text("hello", "a.kt"), previewText("hello", "a.kt"))
    }
}
