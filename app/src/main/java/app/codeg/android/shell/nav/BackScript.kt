package app.codeg.android.shell.nav

/** Content nodes only: open triggers and collapsibles are not dismissible layers. */
internal val dismissLayerScript = """
(function() {
  var selector = '[role="dialog"],[role="alertdialog"],[role="menu"],[role="listbox"],'+
    '[data-slot="popover-content"],[data-slot="dropdown-menu-content"],'+
    '[data-slot="context-menu-content"],[data-slot="drawer-popup"]';
  var layers = Array.from(document.querySelectorAll(selector)).filter(function(el) {
    return el.getAttribute('data-state') !== 'closed' && !el.hasAttribute('data-closed') &&
      !el.hidden && el.getAttribute('aria-hidden') !== 'true' &&
      getComputedStyle(el).visibility !== 'hidden' && el.getClientRects().length > 0;
  });
  if (!layers.length) return false;
  (document.activeElement || document.body).dispatchEvent(new KeyboardEvent('keydown', {
    key: 'Escape', code: 'Escape', keyCode: 27, which: 27, bubbles: true, cancelable: true
  }));
  return true;
})()
""".trimIndent()

/** Separate evaluation lets React commit dismissal before we inspect the DOM again. */
internal val layerStillOpenScript = """
(function() {
  return Array.from(document.querySelectorAll(
    '[role="dialog"],[role="alertdialog"],[role="menu"],[role="listbox"],'+
    '[data-slot="popover-content"],[data-slot="dropdown-menu-content"],'+
    '[data-slot="context-menu-content"],[data-slot="drawer-popup"]'
  )).some(function(el) {
    return el.getAttribute('data-state') !== 'closed' && !el.hasAttribute('data-closed') &&
      !el.hidden && el.getAttribute('aria-hidden') !== 'true' &&
      getComputedStyle(el).visibility !== 'hidden' && el.getClientRects().length > 0;
  });
})()
""".trimIndent()
