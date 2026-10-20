package app.codeg.android.core.update

import app.codeg.android.R
import org.junit.Assert.*
import org.junit.Test

class UpdateBannerVisibilityTest {
    @Test fun availableAndDismissedUpdates() {
        val update = AppUpdate("1.0", 100, "notes", "https://example.com/app.apk", null, 1000L)
        assertTrue(UpdateUi(update = update).showBanner)
        assertFalse(UpdateUi(update = update, dismissed = 100).showBanner)
        assertTrue(UpdateUi(update = update, dismissed = 99).showBanner)
        assertTrue(UpdateUi(update = update, dismissed = 100, downloading = true).showBanner)
    }
    @Test fun onlyActionableStatesShowBanner() {
        assertFalse(UpdateUi().showBanner)
        assertFalse(UpdateUi(checking = true).showBanner)
        assertFalse(UpdateUi(message = R.string.update_latest).showBanner)
        assertFalse(UpdateUi(message = R.string.update_check_failed).showBanner)
        assertFalse(UpdateUi(message = R.string.update_install_failed).showBanner)
        assertTrue(UpdateUi(downloading = true).showBanner)
        assertTrue(UpdateUi(message = R.string.update_download_failed).showBanner)
    }
}
