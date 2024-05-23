package com.renhejia.robot.launcher.displaymode;

import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo;
import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo;
import com.renhejia.robot.display.RobotPlatformState;

public interface RobotDisplayModeListener {
    public void updateGeneralInfo(GeneralInfo generalInfo);
    public void updateWeather(WeatherInfo weatherInfo);
    public void updateCountDown(CountDownListInfo countDownListInfo);
    public void updateNotice(CalenderInfo calenderInfo);
    public void updateFansInfo(FansInfo fansInfo);

}
