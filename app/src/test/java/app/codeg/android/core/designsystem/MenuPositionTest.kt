package app.codeg.android.core.designsystem

import app.codeg.android.core.designsystem.component.menuPopupPosition
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class MenuPositionTest {
    @Test fun opensBelowAnchorWithGap() {
        assertEquals(IntOffset(20, 64), menuPopupPosition(
            IntRect(20, 20, 80, 60), IntSize(300, 400), IntSize(120, 80), LayoutDirection.Ltr, 4,
        ))
    }

    @Test fun opensAboveWhenBottomHasInsufficientSpace() {
        assertEquals(IntOffset(172, 216), menuPopupPosition(
            IntRect(240, 300, 290, 340), IntSize(300, 400), IntSize(120, 80), LayoutDirection.Ltr, 4,
        ))
    }

    @Test fun alignsToRightEdgeInRtl() {
        assertEquals(IntOffset(100, 64), menuPopupPosition(
            IntRect(160, 20, 220, 60), IntSize(300, 400), IntSize(120, 80), LayoutDirection.Rtl, 4,
        ))
    }

    @Test fun keepsEightPixelMarginAtRightEdge() {
        assertEquals(IntOffset(172, 64), menuPopupPosition(
            IntRect(280, 20, 300, 60), IntSize(300, 400), IntSize(120, 80), LayoutDirection.Ltr, 4,
            collisionPadding = 8,
        ))
    }

    @Test fun keepsMarginAtLeftEdgeInRtl() {
        assertEquals(IntOffset(8, 64), menuPopupPosition(
            IntRect(0, 20, 20, 60), IntSize(300, 400), IntSize(120, 80), LayoutDirection.Rtl, 4,
            collisionPadding = 8,
        ))
    }

    @Test fun keepsMarginAtBottomEdge() {
        assertEquals(IntOffset(20, 8), menuPopupPosition(
            IntRect(20, 0, 80, 10), IntSize(300, 100), IntSize(120, 84), LayoutDirection.Ltr, 4,
            collisionPadding = 8,
        ))
    }

    @Test fun oversizedPopupNeverUsesNegativeCoordinates() {
        assertEquals(IntOffset.Zero, menuPopupPosition(
            IntRect(20, 20, 80, 60), IntSize(100, 100), IntSize(120, 120), LayoutDirection.Ltr, 4,
        ))
    }
}
