# src/fontlist/font_reader.py
from fontTools.ttLib import TTFont
from dotenv import load_dotenv
from fontmodel.FontInfo import FontInfo
import os
import json

# Load environment variables from .env file
load_dotenv()
THORTYPE_PATH = os.getenv("THORTYPE_PATH")


def read_font_file(file_path):
    font = TTFont(file_path)
    # Process the font file here
    return font


def extract_glyphs_and_unicode(font_path):
    try:
        font = TTFont(font_path)
        glyph_list = []

        for table in font['cmap'].tables:
            if table.format == 4:
                for code, name in table.cmap.items():
                    glyph_list.append((name, hex(code)))

        return glyph_list
    except:
        print("could not extract from " + font_path)


def print_data_from_glyphs(glyphs_unicode, fontname):
    font_info = FontInfo()
    font_info.fontname = fontname
    for glyph, unicode_code in glyphs_unicode:
        font_info.add_glyph(glyph, unicode_code)
    return font_info


def list_font_files(base_path):
    myList = os.listdir(base_path + "/public/static/fonts/")
    fontinfolist = []
    for fontname in myList:
        fontpath = base_path + "/public/static/fonts/" + fontname
        if fontpath.endswith(".ttf") or fontpath.endswith(".otf"):
            glyphs_unicode = extract_glyphs_and_unicode(fontpath)
            fontinfo = print_data_from_glyphs(glyphs_unicode, fontname)
            fontinfolist.append(fontinfo)
    font_info_dicts = [font_info.to_dict() for font_info in fontinfolist]
    with open("../../fonts.json", "w") as json_file:
        json_file.write(json.dumps(font_info_dicts, separators=(',', ':')))


list_font_files(THORTYPE_PATH)
