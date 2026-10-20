package app.codeg.android.core.designsystem

import app.codeg.android.core.designsystem.component.*
import org.junit.Assert.*
import org.junit.Test

class ToastsTest {
    @Test fun newestThreeAreVisibleAndHiddenToastKeepsItsBudget() {
        val queue = ToastQueue()
        val ids = (1..4).map { queue.add(ToastType.Info, "$it") }
        assertEquals(ids.reversed().take(3), queue.visible.map { it.id })
        queue.advance(4000)
        assertEquals(listOf(ids.first()), queue.visible.map { it.id })
        queue.advance(3999)
        assertEquals(1, queue.visible.size)
        queue.advance(1)
        assertTrue(queue.visible.isEmpty())
    }

    @Test fun loadingNeverExpiresAndUpdateReusesIdAndRestartsDuration() {
        val queue = ToastQueue()
        val id = queue.add(ToastType.Loading, "loading")
        queue.advance(100000)
        assertEquals(id, queue.visible.single().id)
        queue.update(id, ToastType.Success, "done", "description")
        assertEquals(ToastType.Success, queue.visible.single().type)
        assertEquals("description", queue.visible.single().description)
        queue.advance(3999)
        assertEquals(id, queue.visible.single().id)
        queue.advance(1)
        assertTrue(queue.visible.isEmpty())
    }

    @Test fun dismissAndUnknownUpdatesAreSafe() {
        val queue = ToastQueue()
        val id = queue.add(ToastType.Error, "error")
        queue.dismiss(id)
        queue.dismiss(id)
        queue.update(id, ToastType.Warning, "ignored", null)
        assertTrue(queue.visible.isEmpty())
    }

    @Test fun allTypesAndActionArePreserved() {
        val queue = ToastQueue()
        var clicks = 0
        val action = ToastAction("retry") { clicks++ }
        ToastType.values().forEach { type ->
            val id = queue.add(type, type.name, "detail", action)
            assertEquals(type, queue.visible.first().type)
            queue.visible.first().action!!.onClick()
            queue.dismiss(id)
        }
        assertEquals(5, clicks)
    }

    @Test fun updatingErrorClearsDescriptionAndPreservesAction() {
        val queue = ToastQueue()
        val action = ToastAction("retry") {}
        val id = queue.add(ToastType.Loading, "loading", "old", action)
        queue.update(id, ToastType.Error, "failed", null)
        assertNull(queue.visible.single().description)
        assertSame(action, queue.visible.single().action)
        queue.advance(4000)
        assertTrue(queue.visible.isEmpty())
    }

    @Test fun negativeTimeDoesNotExtendLifetime() {
        val queue = ToastQueue()
        queue.add(ToastType.Warning, "warning")
        queue.advance(-100)
        queue.advance(4000)
        assertTrue(queue.visible.isEmpty())
    }
}
