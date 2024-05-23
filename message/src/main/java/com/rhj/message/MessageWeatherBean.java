package com.rhj.message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2023-01-18 17:10:22
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MessageWeatherBean extends MessageBean{


    private ExtraDTO extra;
    private WebhookRespDTO webhookResp;
    private String name;
    private String widgetName;
    private String cityName;
    private String duiWidget;
    private String dm_intent;
    @SerializedName("type")
    private String type1;
    private List<String> recommendations;
    private String intentName;
    private String skillId;
    private String skillName;
    private String taskName;

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public WebhookRespDTO getWebhookResp() {
        return webhookResp;
    }

    public void setWebhookResp(WebhookRespDTO webhookResp) {
        this.webhookResp = webhookResp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDuiWidget() {
        return duiWidget;
    }

    public void setDuiWidget(String duiWidget) {
        this.duiWidget = duiWidget;
    }

    public String getDm_intent() {
        return dm_intent;
    }

    public void setDm_intent(String dm_intent) {
        this.dm_intent = dm_intent;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type) {
        this.type1 = type;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static class ExtraDTO {
        private String nlg_message;

        public String getNlg_message() {
            return nlg_message;
        }

        public void setNlg_message(String nlg_message) {
            this.nlg_message = nlg_message;
        }
    }

    public static class WebhookRespDTO {
        private Integer errorcode;
        private ExtraDTO extra;
        private BrandDTO brand;
        private String cityName;

        public Integer getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(Integer errorcode) {
            this.errorcode = errorcode;
        }

        public ExtraDTO getExtra() {
            return extra;
        }

        public void setExtra(ExtraDTO extra) {
            this.extra = extra;
        }

        public BrandDTO getBrand() {
            return brand;
        }

        public void setBrand(BrandDTO brand) {
            this.brand = brand;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public static class ExtraDTO {
            private List<ForecastDTO> forecast;
            private List<HourForecast24DTO> hourForecast24;
            @com.google.gson.annotations.SerializedName("Index")
            private IndexDTO index;
            private Integer sourceId;
            private ConditionDTO condition;
            private List<FutureDTO> future;

            public List<ForecastDTO> getForecast() {
                return forecast;
            }

            public void setForecast(List<ForecastDTO> forecast) {
                this.forecast = forecast;
            }

            public List<HourForecast24DTO> getHourForecast24() {
                return hourForecast24;
            }

            public void setHourForecast24(List<HourForecast24DTO> hourForecast24) {
                this.hourForecast24 = hourForecast24;
            }

            public IndexDTO getIndex() {
                return index;
            }

            public void setIndex(IndexDTO index) {
                this.index = index;
            }

            public Integer getSourceId() {
                return sourceId;
            }

            public void setSourceId(Integer sourceId) {
                this.sourceId = sourceId;
            }

            public ConditionDTO getCondition() {
                return condition;
            }

            public void setCondition(ConditionDTO condition) {
                this.condition = condition;
            }

            public List<FutureDTO> getFuture() {
                return future;
            }

            public void setFuture(List<FutureDTO> future) {
                this.future = future;
            }

            public static class IndexDTO {
                private List<LiveIndexDTO> liveIndex;
                private AqiDTO aqi;
                private String humidity;

                public List<LiveIndexDTO> getLiveIndex() {
                    return liveIndex;
                }

                public void setLiveIndex(List<LiveIndexDTO> liveIndex) {
                    this.liveIndex = liveIndex;
                }

                public AqiDTO getAqi() {
                    return aqi;
                }

                public void setAqi(AqiDTO aqi) {
                    this.aqi = aqi;
                }

                public String getHumidity() {
                    return humidity;
                }

                public void setHumidity(String humidity) {
                    this.humidity = humidity;
                }

                public static class AqiDTO {
                    @com.google.gson.annotations.SerializedName("AQIdesc")
                    private String aQIdesc;
                    private String pm25;
                    @com.google.gson.annotations.SerializedName("AQL")
                    private String aQL;
                    @com.google.gson.annotations.SerializedName("AQI")
                    private String aQI;

                    public String getAQIdesc() {
                        return aQIdesc;
                    }

                    public void setAQIdesc(String aQIdesc) {
                        this.aQIdesc = aQIdesc;
                    }

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getAQL() {
                        return aQL;
                    }

                    public void setAQL(String aQL) {
                        this.aQL = aQL;
                    }

                    public String getAQI() {
                        return aQI;
                    }

                    public void setAQI(String aQI) {
                        this.aQI = aQI;
                    }
                }

                public static class LiveIndexDTO {
                    private String name;
                    private String desc;
                    private String status;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }
                }
            }

            public static class ConditionDTO {
                private String windLevel;
                private String weather;
                private String wind;
                private String temperature;

                public String getWindLevel() {
                    return windLevel;
                }

                public void setWindLevel(String windLevel) {
                    this.windLevel = windLevel;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }
            }

            public static class ForecastDTO {
                private String lowTemp;
                private String sunrise;
                private String tempInteval;
                private String weather;
                private String temperature;
                private String windLevel;
                private String tip;
                private String highTemp;
                private String week;
                private String wind;
                private String sunset;
                private String tempTip;
                private String date;

                public String getLowTemp() {
                    return lowTemp;
                }

                public void setLowTemp(String lowTemp) {
                    this.lowTemp = lowTemp;
                }

                public String getSunrise() {
                    return sunrise;
                }

                public void setSunrise(String sunrise) {
                    this.sunrise = sunrise;
                }

                public String getTempInteval() {
                    return tempInteval;
                }

                public void setTempInteval(String tempInteval) {
                    this.tempInteval = tempInteval;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWindLevel() {
                    return windLevel;
                }

                public void setWindLevel(String windLevel) {
                    this.windLevel = windLevel;
                }

                public String getTip() {
                    return tip;
                }

                public void setTip(String tip) {
                    this.tip = tip;
                }

                public String getHighTemp() {
                    return highTemp;
                }

                public void setHighTemp(String highTemp) {
                    this.highTemp = highTemp;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getSunset() {
                    return sunset;
                }

                public void setSunset(String sunset) {
                    this.sunset = sunset;
                }

                public String getTempTip() {
                    return tempTip;
                }

                public void setTempTip(String tempTip) {
                    this.tempTip = tempTip;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

            public static class HourForecast24DTO {
                private String processTime;
                private String weather;
                private Integer temperature;

                public String getProcessTime() {
                    return processTime;
                }

                public void setProcessTime(String processTime) {
                    this.processTime = processTime;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public Integer getTemperature() {
                    return temperature;
                }

                public void setTemperature(Integer temperature) {
                    this.temperature = temperature;
                }
            }

            public static class FutureDTO {
                private String windLevel;
                private String wind;
                private String date;
                private String week;
                private String weather;
                private String temperature;

                public String getWindLevel() {
                    return windLevel;
                }

                public void setWindLevel(String windLevel) {
                    this.windLevel = windLevel;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }
            }
        }

        public static class BrandDTO {
            private String isexport;
            private String logoMiddle;
            private String logoSmall;
            private String name;
            private String showName;
            private String logoLarge;

            public String getIsexport() {
                return isexport;
            }

            public void setIsexport(String isexport) {
                this.isexport = isexport;
            }

            public String getLogoMiddle() {
                return logoMiddle;
            }

            public void setLogoMiddle(String logoMiddle) {
                this.logoMiddle = logoMiddle;
            }

            public String getLogoSmall() {
                return logoSmall;
            }

            public void setLogoSmall(String logoSmall) {
                this.logoSmall = logoSmall;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShowName() {
                return showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public String getLogoLarge() {
                return logoLarge;
            }

            public void setLogoLarge(String logoLarge) {
                this.logoLarge = logoLarge;
            }
        }
    }
}