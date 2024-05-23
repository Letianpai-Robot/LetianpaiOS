package com.renhejia.robot.commandlib.parser.displaymodes.logo;

public class LogoData {
    private String  hello_logo;

    private String  desktop_logo;

    public String getHello_logo() {
        return hello_logo;
    }

    public void setHello_logo(String hello_logo) {
        this.hello_logo = hello_logo;
    }

    public String getDesktop_logo() {
        return desktop_logo;
    }

    public void setDesktop_logo(String desktop_logo) {
        this.desktop_logo = desktop_logo;
    }

    @Override
    public String toString() {
        return "LogoData{" +
                "hello_logo='" + hello_logo + '\'' +
                ", desktop_logo='" + desktop_logo + '\'' +
                '}';
    }
}
