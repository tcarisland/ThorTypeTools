<?php

class GDToolsMain
{
  public function __construct() {
      echo 'GDToolsMain initialized';
  }

  public function run() {
      echo "run called";
  }
}

$myGDToolsMain = new GDToolsMain();
$myGDToolsMain->run();

