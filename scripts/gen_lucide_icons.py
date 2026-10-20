#!/usr/bin/env python3
"""Generate Compose vectors from lucide-static 0.564.0; Python standard library only.

Usage: python3 scripts/gen_lucide_icons.py --svg-dir /tmp/lucide/package/icons \
    --web-root /mnt/e/claude/codeg/src
Without --web-root, use the checked-in manifest (reproducible offline generation).
"""
import argparse
import json
import re
from pathlib import Path
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'app/src/main/java/app/codeg/android/core/designsystem/icon'
HEADER = '''// 由脚本生成，勿手改。 Source: lucide-static@0.564.0.
// ISC License
//
// Copyright (c) for portions of Lucide are held by Cole Bemis 2013-2026 as part of Feather (MIT). All other copyright (c) for Lucide are held by Lucide Contributors 2026.
//
// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
//
// ---
//
// The MIT License (MIT) (for portions derived from Feather)
//
// Copyright (c) 2013-2026 Cole Bemis
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
package app.codeg.android.core.designsystem.icon

'''
IMPORTS = '''import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

'''

def import_names(text):
    result = set()
    for body in re.findall(r'import\s*\{([^}]+)\}\s*from\s*[\'"]lucide-react[\'"]', text):
        body = re.sub(r'/\*.*?\*/|//[^\n]*', '', body, flags=re.S)
        for item in body.split(','):
            item = item.strip()
            if not item or item.startswith('type '):
                continue
            name = re.split(r'\s+as\s+', item)[0].strip()
            if name in {'LucideIcon', 'LucideProps', 'IconNode'}:
                continue
            result.add(name.removesuffix('Icon'))
    return result


def kebab(name):
    text = re.sub(r'([A-Z]+)([A-Z][a-z])', r'\1-\2', name)
    text = re.sub(r'([a-z0-9])([A-Z])', r'\1-\2', text)
    return re.sub(r'([a-wyzA-WYZ])([0-9])', r'\1-\2', text).lower()


def path_data(el):
    tag = el.tag.rsplit('}', 1)[-1]
    a = el.attrib
    def n(key, default=0):
        return float(a.get(key, default))
    if tag == 'path':
        return a['d']
    if tag == 'line':
        return f'M{n("x1")} {n("y1")}L{n("x2")} {n("y2")}'
    if tag in {'polyline', 'polygon'}:
        p = re.findall(r'[-+]?(?:\d*\.\d+|\d+\.?\d*)(?:[eE][-+]?\d+)?', a['points'])
        if len(p) % 2 or len(p) < 4:
            raise ValueError('Invalid points')
        return 'M' + ' '.join(p[:2]) + ''.join('L' + ' '.join(p[i:i+2]) for i in range(2, len(p), 2)) + ('Z' if tag == 'polygon' else '')
    if tag in {'circle', 'ellipse'}:
        x, y = n('cx'), n('cy')
        rx, ry = (n('r'), n('r')) if tag == 'circle' else (n('rx'), n('ry'))
        return f'M{x-rx} {y}A{rx} {ry} 0 1 0 {x+rx} {y}A{rx} {ry} 0 1 0 {x-rx} {y}Z'
    if tag == 'rect':
        x, y, w, h = n('x'), n('y'), n('width'), n('height')
        rx = min(n('rx', a.get('ry', 0)), w / 2)
        ry = min(n('ry', a.get('rx', 0)), h / 2)
        if rx == 0 or ry == 0:
            return f'M{x} {y}h{w}v{h}h{-w}Z'
        return (f'M{x+rx} {y}H{x+w-rx}A{rx} {ry} 0 0 1 {x+w} {y+ry}'
                f'V{y+h-ry}A{rx} {ry} 0 0 1 {x+w-rx} {y+h}'
                f'H{x+rx}A{rx} {ry} 0 0 1 {x} {y+h-ry}'
                f'V{y+ry}A{rx} {ry} 0 0 1 {x+rx} {y}Z')
    raise ValueError(f'Unsupported SVG element: {tag}')


def generate(svg_dir, names):
    groups = {label: [] for label in ['A_F', 'G_L', 'M_R', 'S_Z']}
    for name, filename in sorted(names.items()):
        svg = ET.parse(svg_dir / (filename + '.svg')).getroot()
        if svg.get('viewBox') != '0 0 24 24':
            raise ValueError(f'Unexpected viewport: {filename}')
        paths = []
        for el in svg:
            if 'transform' in el.attrib:
                raise ValueError(f'Unsupported transform: {filename}')
            paths.append(path_data(el))
        group = 'A_F' if name[0] <= 'F' else 'G_L' if name[0] <= 'L' else 'M_R' if name[0] <= 'R' else 'S_Z'
        lines = [f'val Lucide.{name}: ImageVector get() = icon{name}', f'private val icon{name}: ImageVector by lazy {{',
                 f'    ImageVector.Builder(name = "Lucide.{name}", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f).apply {{']
        for d in paths:
            lines.append('        addPath(pathData = PathParser().parsePathString(' + json.dumps(d) + ').toNodes(), fill = null, stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round)')
        lines.extend(['    }.build()', '}', ''])
        groups[group].extend(lines)
    OUT.mkdir(parents=True, exist_ok=True)
    for label, lines in groups.items():
        content = HEADER + IMPORTS + '\n'.join(lines)
        if len(content.splitlines()) > 3000:
            raise ValueError(f'Group {label} exceeds 3000 lines')
        (OUT / f'LucideIcons{label}.kt').write_text(content, encoding='utf-8')
    # Chunk registries so neither a class initializer nor method grows with all icons.
    registry = ['object Lucide', '', 'internal val lucideGallery: List<Pair<String, () -> ImageVector>>', '    get() = galleryA_F() + galleryG_L() + galleryM_R() + galleryS_Z()', '']
    for label in groups:
        registry.append(f'private fun gallery{label}(): List<Pair<String, () -> ImageVector>> = listOf(')
        for name in sorted(names):
            group = 'A_F' if name[0] <= 'F' else 'G_L' if name[0] <= 'L' else 'M_R' if name[0] <= 'R' else 'S_Z'
            if group == label:
                registry.append(f'    "{name}" to {{ Lucide.{name} }},')
        registry.extend([')', ''])
    (OUT / 'LucideIcons.kt').write_text(HEADER + 'import androidx.compose.ui.graphics.vector.ImageVector\n\n' + '\n'.join(registry), encoding='utf-8')


def self_test():
    """Executable conversion/import regression checks; no Android toolchain needed."""
    import unittest

    class GeneratorTests(unittest.TestCase):
        def test_import_aliases(self):
            source = 'import {\n XIcon as Close, type LucideIcon, Calendar as CalendarIcon,\n} from "lucide-react"'
            self.assertEqual(import_names(source), {'X', 'Calendar'})
            self.assertEqual(kebab('CheckCircle2'), 'check-circle-2')
            self.assertEqual(kebab('Grid2x2'), 'grid-2x2')

        def test_all_shapes(self):
            cases = [
                ('path d="M1 2h3"', 'M1 2h3'),
                ('line x1="1" y1="2" x2="3" y2="4"', 'M1.0 2.0L3.0 4.0'),
                ('polyline points="1,2 3,4"', 'M1 2L3 4'),
                ('polygon points="1,2 3,4 5,6"', 'M1 2L3 4L5 6Z'),
                ('rect x="1" y="2" width="3" height="4"', 'M1.0 2.0h3.0v4.0h-3.0Z'),
                ('circle cx="12" cy="12" r="4"', 'M8.0 12.0A4.0 4.0 0 1 0 16.0 12.0A4.0 4.0 0 1 0 8.0 12.0Z'),
                ('ellipse cx="12" cy="12" rx="4" ry="2"', 'M8.0 12.0A4.0 2.0 0 1 0 16.0 12.0A4.0 2.0 0 1 0 8.0 12.0Z'),
            ]
            for attributes, expected in cases:
                with self.subTest(attributes=attributes):
                    self.assertEqual(path_data(ET.fromstring('<' + attributes + '/>')), expected)

        def test_rounded_rect_radii(self):
            def rect(attributes):
                return path_data(ET.fromstring('<rect width="8" height="6" ' + attributes + '/>'))
            self.assertEqual(rect('rx="2"'), rect('ry="2"'))
            self.assertEqual(rect('rx="99" ry="99"'), rect('rx="4" ry="3"'))
            self.assertIn('A2.0 1.0', rect('rx="2" ry="1"'))
            self.assertNotIn('A', rect('rx="0" ry="2"'))

        def test_unsupported_element_fails(self):
            with self.assertRaises(ValueError):
                path_data(ET.fromstring('<g/>'))

    result = unittest.TextTestRunner(verbosity=2).run(unittest.defaultTestLoader.loadTestsFromTestCase(GeneratorTests))
    if not result.wasSuccessful():
        raise SystemExit(1)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--svg-dir', type=Path)
    parser.add_argument('--self-test', action='store_true')
    parser.add_argument('--web-root', type=Path)
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if args.svg_dir is None:
        parser.error('--svg-dir is required unless --self-test is used')
    manifest = ROOT / 'scripts/lucide_icons.txt'
    names = {}
    if args.web_root:
        for file in sorted(args.web_root.rglob('*.tsx')):
            for name in import_names(file.read_text(encoding='utf-8')):
                names[name] = kebab(name)
        mapping = (ROOT / '.taskmaster/docs/icon-map.md').read_text(encoding='utf-8')
        for name in re.findall(r'^\| Icons\.[^|]+\| `([A-Za-z0-9]+)`', mapping, re.M):
            names[name] = kebab(name)
        available = {p.stem for p in args.svg_dir.glob('*.svg')}
        for name, filename in names.items():
            if filename not in available:
                matches = sorted(p for p in available if p.replace('-', '') == filename.replace('-', ''))
                if not matches:
                    raise ValueError(f'No SVG for {name}: {filename}')
                names[name] = matches[0]
        manifest.write_text('\n'.join(sorted(set(names.values()))) + '\n', encoding='utf-8')
    else:
        names = {''.join(part.capitalize() for part in line.split('-')): line for line in manifest.read_text().splitlines() if line}
    generate(args.svg_dir, names)
    print(f'Generated {len(names)} icons')

if __name__ == '__main__':
    main()
