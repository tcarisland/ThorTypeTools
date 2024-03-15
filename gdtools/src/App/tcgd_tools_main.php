<?php

namespace tcgdtools;

class tcgd_tools_main
{
    private $properties;

    public function __construct($properties) {
        $this->properties = $properties;
    }

    public function get_first_fontpath() {
        reset($this->properties);
        $fontname = $this->properties['fonts'][key($this->properties['fonts'])];
        $basepath = $this->properties['basepath'];
        return $basepath . "/" . $fontname;
    }

    public function run() {
        echo $this->get_first_fontpath();
    }
}
