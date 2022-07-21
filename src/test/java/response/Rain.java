package response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rain
{
    @JsonProperty("1h")
    private String h1;

    @JsonIgnore
    public String get1h ()
    {
        return h1;
    }

    public void set1h (String h1)
    {
        this.h1 = h1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [1h = "+h1+"]";
    }
}