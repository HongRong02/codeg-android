package app.codeg.android.shell.web

import org.json.JSONObject

/** Only text APIs are emulated; image APIs retain browser capability detection. */
internal fun clipboardScript(origin: String, token: String): String {
    val originJson = JSONObject.quote(origin)
    val tokenJson = JSONObject.quote(token)
    return """
        (() => {
          if (location.origin !== $originJson || window.__codegClipboardInstalled) return;
          const token = $tokenJson;
          const clipboard = navigator.clipboard || {};
          for (const name of ['writeText', 'readText']) {
            const native = typeof clipboard[name] === 'function' ? clipboard[name].bind(clipboard) : null;
            const fallback = (...args) => {
              if (name === 'writeText') {
                if (!CodegShellClipboard.writeText(token, String(args[0]))) throw new Error('Clipboard unavailable');
                return;
              }
              const text = CodegShellClipboard.readText(token);
              if (text === null) throw new Error('Clipboard unavailable');
              return text;
            };
            Object.defineProperty(clipboard, name, {
              configurable: true,
              value: async (...args) => {
                if (native) {
                  try { return await native(...args); } catch (_) {}
                }
                return fallback(...args);
              }
            });
          }
          if (!navigator.clipboard) Object.defineProperty(navigator, 'clipboard', { configurable: true, value: clipboard });
          window.__codegClipboardInstalled = true;
        })();
    """.trimIndent()
}
