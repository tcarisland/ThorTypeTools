# tests/test_font_reader.py
from src.fontlist.font_reader import read_font_file

def test_read_font_file():
    path = "../../../../thortype/public/static/fonts/belleview.otf"
    font = read_font_file(path)
    assert font is not None
    # Add more assertions as needed
