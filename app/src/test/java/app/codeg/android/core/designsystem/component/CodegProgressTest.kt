package app.codeg.android.core.designsystem.component

import org.junit.Assert.assertEquals
import org.junit.Test

class CodegProgressTest {
    @Test
    fun preservesFractionalProgress() {
        assertEquals(0.35f, normalizedProgress(0.35f), 0f)
    }

    @Test
    fun clampsOutOfRangeProgress() {
        assertEquals(0f, normalizedProgress(-0.2f), 0f)
        assertEquals(1f, normalizedProgress(1.2f), 0f)
        assertEquals(0f, normalizedProgress(Float.NEGATIVE_INFINITY), 0f)
        assertEquals(1f, normalizedProgress(Float.POSITIVE_INFINITY), 0f)
    }

    @Test
    fun treatsMissingNumericProgressAsEmpty() {
        assertEquals(0f, normalizedProgress(Float.NaN), 0f)
        assertEquals(0f, normalizedProgress(0f), 0f)
        assertEquals(1f, normalizedProgress(1f), 0f)
    }
}
