# tests/test_font_reader.py
from src.fontlist.font_reader import read_font_file
from dotenv import load_dotenv
import os

# Load environment variables from .env file
load_dotenv()
THORTYPE_PATH = os.getenv("THORTYPE_PATH")
# Access environment variables
def test_read_font_file():
    path = str(THORTYPE_PATH) + "/public/static/fonts/belleview.otf"
    font = read_font_file(path)
    assert font is not None
    # Add more assertions as needed
