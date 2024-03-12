package com.tcarisland.thortype.fontinspector;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
public class FontInspectorProperties {

    @Value("${width}")
    private int width;

    @Value("${height}")
    private int height;

}
