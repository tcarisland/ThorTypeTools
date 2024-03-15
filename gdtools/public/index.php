<?php

namespace tcgdtools;

// Include the tcgd_tools_main.php file
require '../src/App/tcgd_tools_main.php';
$properties = require '../src/App/config/config.php';

$tcgdtools = new tcgd_tools_main($properties);
// Call the run method directly on the tcgd_tools_main instance
$tcgdtools->run();
