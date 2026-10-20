package app.codeg.android.core.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPath
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class LucideIconsTest {
    @Test
    fun allIconsHaveLucideViewportAndStroke() {
        val icons = lucideGallery
        assertEquals(325, icons.size)
        assertEquals(icons.size, icons.map { it.first }.toSet().size)
        icons.forEach { (name, vector) ->
            val icon = vector()
            assertEquals(name, "Lucide.$name", icon.name)
            assertEquals(24f, icon.viewportWidth, 0f)
            assertEquals(24f, icon.viewportHeight, 0f)
            assertEquals(24.dp, icon.defaultWidth)
            assertEquals(24.dp, icon.defaultHeight)
            assertSame(icon, vector())
            assertTrue(name, icon.root.size > 0)
            for (index in 0 until icon.root.size) {
                val path = icon.root[index] as VectorPath
                assertTrue(name, path.pathData.isNotEmpty())
                assertNull(path.fill)
                assertNotNull(path.stroke)
                assertEquals(SolidColor(Color.Black), path.stroke)
                assertEquals(2f, path.strokeLineWidth, 0f)
                assertEquals(StrokeCap.Round, path.strokeLineCap)
                assertEquals(StrokeJoin.Round, path.strokeLineJoin)
            }
        }
    }

    @Test
    fun commonIconsPreserveUpstreamElementCounts() {
        // Counts independently sampled from lucide-static@0.564.0 SVG children.
        val samples: List<Pair<ImageVector, Int>> = listOf(
            Lucide.MessageSquare to 1, Lucide.RefreshCw to 4,
            Lucide.Ellipsis to 3, Lucide.EllipsisVertical to 3,
            Lucide.Search to 2, Lucide.Plus to 2, Lucide.X to 2,
            Lucide.Check to 1, Lucide.ChevronDown to 1, Lucide.ChevronRight to 1,
            Lucide.Folder to 1, Lucide.FolderOpen to 1, Lucide.Copy to 2,
            Lucide.Trash2 to 5, Lucide.Pencil to 2, Lucide.ArrowLeft to 2,
            Lucide.ArrowUp to 2, Lucide.Square to 1, Lucide.Circle to 1,
            Lucide.ShieldAlert to 3, Lucide.Package to 4, Lucide.Brain to 8,
            Lucide.Calendar to 4, Lucide.Loader2 to 1,
        )
        samples.forEach { (icon, count) -> assertEquals(icon.name, count, icon.root.size) }
    }
}
