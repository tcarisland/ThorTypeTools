<?php

namespace tcgdtools;

// Include the tcgd_tools_main.php file
require '../../src/App/tcgd_tools_main.php';
$properties = require '../../src/App/config/config.php';

$tcgdtools = new tcgd_tools_main($properties);
// Call the run method directly on the tcgd_tools_main instance
$font_path = $tcgdtools->get_first_fontpath();

// Create the image
$im = imagecreatetruecolor(2800, 500);

// Create some colors
$white = imagecolorallocate($im, 255, 255, 255);
$grey = imagecolorallocate($im, 128, 128, 128);
$black = imagecolorallocate($im, 0, 0, 0);
imagefilledrectangle($im, 0, 0, 2799, 499, $white);

// The text to draw
$text = 'the quick brown fox jumps over the lazy dog !?()';
$text2 = 'THE FIVE BOXING WIZARDS JUMP VEXINGLY';
$text3 = 'aVVAWTLTOPjgi VyYv Toqueville 01234567890 ! A?';
// Replace path by your own font path
$font = $font_path;

// Add the text
imagettftext($im, 60, 0, 10, 80, $black, $font, $text);
imagettftext($im, 60, 0, 10, 200, $black, $font, $text2);
imagettftext($im, 60, 0, 10, 300, $black, $font, $text3);

// Start output buffering
ob_start();

// Using imagepng() to generate PNG image data
imagepng($im);

// Capture the output buffer contents
$image_data = ob_get_clean();

// Destroy the image resource
imagedestroy($im);

// Encode the image data as base64
$base64_image = base64_encode($image_data);

// Output the base64-encoded image data as a data URI
echo '<img src="' . 'data:image/png;base64,' . $base64_image . '"/>';
