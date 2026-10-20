package app.codeg.android.shell.display

/** Runs after Compose has resized the WebView, without changing the server's layout. */
internal val imeFocusObserver: String = """
    (() => {
      if (window.__codegShellDisplayImeCleanup) window.__codegShellDisplayImeCleanup();
      let timer = 0;
      const cancel = () => { clearTimeout(timer); timer = 0; };
      const reveal = () => {
        timer = 0;
        const element = document.activeElement;
        if (!element || element.disabled || element.readOnly) return;
        const editable = element.isContentEditable || element.tagName === 'TEXTAREA' ||
          (element.tagName === 'INPUT' &&
            /^(text|search|email|url|tel|password|number)$/.test(element.type));
        if (!editable) return;
        const rect = element.getBoundingClientRect();
        if (rect.bottom <= rect.top) return;
        let top = 0, bottom = innerHeight, left = 0, right = innerWidth;
        // The workspace and rich composer also clip inside nested scroll containers.
        for (let parent = element.parentElement; parent; parent = parent.parentElement) {
          const style = getComputedStyle(parent);
          const bounds = parent.getBoundingClientRect();
          if (/^(auto|scroll|hidden|clip)$/.test(style.overflowY)) {
            const edge = bounds.top + parent.clientTop;
            top = Math.max(top, edge);
            bottom = Math.min(bottom, edge + parent.clientHeight);
          }
          if (/^(auto|scroll|hidden|clip)$/.test(style.overflowX)) {
            const edge = bounds.left + parent.clientLeft;
            left = Math.max(left, edge);
            right = Math.min(right, edge + parent.clientWidth);
          }
        }
        if (rect.top < top || rect.bottom > bottom || rect.left < left || rect.right > right) {
          // Instant avoids CSS scroll-behavior:smooth fighting the IME animation.
          element.scrollIntoView({block: 'nearest', inline: 'nearest', behavior: 'instant'});
        }
      };
      const schedule = () => {
        cancel();
        // Trailing debounce: do not scroll once for every IME animation layout.
        timer = setTimeout(reveal, 120);
      };
      window.__codegShellDisplayIme = shrinking => { if (shrinking) schedule(); else cancel(); };
      document.addEventListener('focusin', schedule);
      window.__codegShellDisplayImeCleanup = () => {
        cancel();
        document.removeEventListener('focusin', schedule);
        delete window.__codegShellDisplayIme;
        delete window.__codegShellDisplayImeCleanup;
      };
    })();
""".trimIndent()
