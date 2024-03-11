# src/fontlist/font_reader.py
from fontTools.ttLib import TTFont

def read_font_file(file_path):
    font = TTFont(file_path)
    # Process the font file here
    return font
