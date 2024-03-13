# Font Inspector

This is a tool to inspect TrueType and OpenType fonts to study their metadata and how the font is built up.

The project is an exercise in using Java Reflection to recursiely create a tree of information about the object.

![image](https://github.com/tcarisland/thortypetools/assets/11506194/f0a03314-3ddf-4428-9fe8-1e37ceb57bd1)

## Run it

You can run it as a spring-boot application.

Remember to set the **basedir** variable to something useful.

Run it with 

`mvn spring-boot:run -Djava.awt.headless=false`
