package app.codeg.android.shell.download

import org.json.JSONObject

/** Capture the Blob synchronously before callers revoke its object URL. Chunk one slice at a time. */
internal fun downloadScript(secret: String): String = """
(function() {
  if (window !== window.top) return;
  const bridge = window.CodegShellDownload, secret = ${JSONObject.quote(secret)};
  const transfer = async function(url, name, mime) {
    const id = Math.random().toString(36).slice(2) + Date.now();
    try {
      const responsePromise = fetch(url);
      const response = await responsePromise;
      const blob = await response.blob();
      if (!bridge.start(secret, id, name || '', mime || blob.type)) return;
      for (let offset = 0; offset < blob.size; offset += 49152) {
        const chunk = await new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.onload = () => resolve(reader.result.split(',')[1]);
          reader.onerror = reject;
          reader.readAsDataURL(blob.slice(offset, offset + 49152));
        });
        if (!bridge.chunk(secret, id, chunk)) throw new Error('Transfer cancelled');
      }
      bridge.end(secret, id);
    } catch (_) { bridge.fail(secret, id); }
  };
  window.__codegDownload = transfer;
  const handle = function(a) {
    const url = a.href;
    if (!a.hasAttribute('download') && !url.startsWith('blob:') && !url.startsWith('data:')) return false;
    if (url.startsWith('blob:')) { transfer(url, a.download, ''); return true; }
    if (/^(https?:|data:)/.test(url)) { bridge.link(secret, url, a.download || ''); return true; }
    return false;
  };
  // Replace handlers on repeated page-finished callbacks without stacking wrappers.
  window.__codegDownloadHandle = handle;
  if (!window.__codegDownloadInstalled) {
    window.__codegDownloadInstalled = true;
    const click = HTMLAnchorElement.prototype.click;
    HTMLAnchorElement.prototype.click = function() {
      if (!window.__codegDownloadHandle(this)) click.call(this);
    };
    document.addEventListener('click', function(event) {
      const a = event.target instanceof Element && event.target.closest('a');
      if (a && window.__codegDownloadHandle(a)) { event.preventDefault(); event.stopImmediatePropagation(); }
    }, true);
  }
})();
""".trimIndent()
