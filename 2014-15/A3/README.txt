Micah Stairs and Will Fiset

The build.xml file allows you to build and run the Mandelbrot GUI application.
Alternatively, you can run our code as you normally would (MandelbrotGUI.java
and Complex.java can be found the in src folder). Using this build file automates
the compilation and running of our program, as well as ensuring that our program's
icon is displayed.

Here are the steps if you choose to use Apache Ant:

1) Install Apache Ant

    Linux (Ubuntu Distro):
        - sudo apt-get install ant

    Mac:
        Get homebrew:
        - ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
        - brew install ant

    Windows:
        
        GOTO:
        - http://archive.apache.org/dist/ant/
        
        GET:
        - ant-current-bin.zip   
        - Unzip and place somewhere, then add ant to the PATH:
        - Set PATH=%PATH%; path_to_ant_bin 
        
        NOTE:
        - My JAVA_HOME environment variable was not set which caused ant to crash
        - Set JAVA_HOME to your jdk path if this happens.


2) Execute the command:
    
    ant run

    We also included extra targets:

    ant clean
    ant build
    ant makejar
    ant test













