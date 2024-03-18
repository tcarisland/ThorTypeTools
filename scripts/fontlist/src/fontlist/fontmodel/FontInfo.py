class FontInfo:
    def __init__(self):
        self._fontname = None
        self._glyphs = {}

    @property
    def fontname(self):
        return self._fontname

    @fontname.setter
    def fontname(self, value):
        self._fontname = value

    @property
    def glyphs(self):
        return self._glyphs

    def add_glyph(self, glyph, unicode_code):
        self._glyphs[glyph] = unicode_code;

    def to_dict(self):
        return {"fontname": self.fontname, "glyphlen": len(self._glyphs), "glyphs": self._glyphs}
