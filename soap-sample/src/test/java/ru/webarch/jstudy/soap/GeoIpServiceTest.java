package ru.webarch.jstudy.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class GeoIpServiceTest {

    @Test
    public void testGeoIpService() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("95.25.133.109");
        assertThat(geoIP.getCountryCode(), equalTo("RUS"));
    }

    @Test
    public void testInvalidIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("95.25.133.xxx");
        assertThat(geoIP.getCountryCode(), not(equalTo("RUS")));
    }


}
