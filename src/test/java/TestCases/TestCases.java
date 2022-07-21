package TestCases;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.GetUserDetails;
import response.List;
import response.Main;
import response.Weather;

import static io.restassured.RestAssured.given;

public class TestCases {

    public Response getResponse(){
        Response response = given()
                .when()
                .get("https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22");

        response.then();/*.log().body();*/
        return response;
    }

    @Test
    public void isResponseContains4DaysOfData(){
        int statusCode = getResponse().statusCode();
        Assert.assertEquals(statusCode,200);

        GetUserDetails getUserDetails = getResponse().as(GetUserDetails.class);
        int length = getUserDetails.getList().length;
        Assert.assertEquals(length,96);
    }

    @Test
    public void weatherIdIs500LightRain(){
        GetUserDetails getUserDetails = getResponse().as(GetUserDetails.class);
        List[] list = getUserDetails.getList();

        validateResponse(getUserDetails, list, "500", "light rain");
    }

    @Test
    public void weatherIdIs800ClearSky(){
        GetUserDetails getUserDetails = getResponse().as(GetUserDetails.class);
        List[] list = getUserDetails.getList();

        validateResponse(getUserDetails, list, "800", "clear sky");
    }

    @Test
    public void forAll4DaystempShouldNotBeLessThanMinTempAndNotMoreThanMaxTemp(){
        GetUserDetails getUserDetails = getResponse().as(GetUserDetails.class);
        List[] list = getUserDetails.getList();

        for(int i = 0; i< getUserDetails.getList().length; i++){
            Main main = list[i].getMain();
            double temp = Double.parseDouble(main.getTemp());
            double temp_min = Double.parseDouble(main.getTemp_min());
            double temp_max = Double.parseDouble(main.getTemp_max());

            if((temp>=temp_min)&&(temp<=temp_max)){
                Assert.assertTrue(true);
            }
        }
    }

    @Test
    public void forecastInterval(){
        GetUserDetails getUserDetails = getResponse().as(GetUserDetails.class);
        List[] list = getUserDetails.getList();

        for(int i = 0; i< getUserDetails.getList().length; i++){
            String dt_txt = list[i].getDt_txt();
            String[] split = dt_txt.split(":");
            System.out.println(split[0]);
        }
    }

    private void validateResponse(GetUserDetails getUserDetails, List[] list, String expectedID, String expectedDescription) {
        for(int i = 0; i< getUserDetails.getList().length; i++){
            Weather[] weather = list[i].getWeather();
            String id = weather[0].getId();
            if(id.equals(expectedID)){
                String description = weather[0].getDescription();
                Assert.assertEquals(description, expectedDescription);
            }
        }
    }
}
