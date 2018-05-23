package io.jbehavetutorials.constants;

import java.io.File;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class ConfigPath {
    public static final String separator = File.separator;
    public static final String CHROME_DRIVER_PATH = "src"+separator+"main"+separator+"resources"+separator+"drivers"+ separator+"chromedriver_";
    public static final String GECKO_DRIVER_PATH = "src"+separator+"main"+separator+"resources"+separator+"drivers"+separator+"geckodriver_";
    public static final String IE_DRIVER_PATH = "src"+separator+"main"+separator+"resources"+separator+"drivers"+separator+"IEDriverServer_win.exe";
}
