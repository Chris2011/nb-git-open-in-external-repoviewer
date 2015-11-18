/* 
 * Copyright 2015 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.markiewb.netbeans.plugin.git.openinexternalviewer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.openide.modules.OnStart;
import org.openide.util.NbPreferences;

/**
 * Migrates the configuration to the patterns of the newest configuration.
 *
 * @author markiewb
 */
@OnStart
public class ConfigurationMigratorAtStartup implements Runnable {

    private static final Logger LOG = Logger.getLogger(ConfigurationMigratorAtStartup.class.getName());
    private static final String MD5_1_4_0_2_sorted = "2813f23fc064cc1830be58909f2201c6";
    private static final String MD5_1_4_0_2_unsorted = "bd3017009088330c55feebf67fce931d";
    private static final String MD5_1_4_1 = "ea7fc67482b391f2660d64ad04606b41";

    @Override
    public void run() {
        Preferences pref = NbPreferences.forModule(Options.class);
        String load = new Options().load(pref);
        if (null == load || load.isEmpty()) {
            //no config found, a fresh new installation -> nothing to migrate
            LOG.fine("no config found, it is a fresh new installation -> nothing to migrate");
            return;
        }
        String mD5 = getMD5(load);
        if (MD5_1_4_1.equals(mD5)) {
            LOG.fine("Found a configuration from the newest version -> NOP");
            return;
        }
        if (MD5_1_4_0_2_sorted.equals(mD5)) {
            //found the old configuration from 1.4.0.2 -> reset to new configuration
            LOG.info("Found the old configuration from 1.4.0.2 -> reset to new configuration");
            new Options().resetToDefault();
            return;
        }
        if (MD5_1_4_0_2_unsorted.equals(mD5)) {
            //found the old configuration from 1.4.0.2 -> reset to new configuration
            LOG.info("Found the old configuration from 1.4.0.2 -> reset to new configuration");
            new Options().resetToDefault();
            return;
        }
        {
            LOG.info("Cannot migrate automatically. Found a manually altered configuration for the 'Open URL at GIT repository hoster' plugin.\nIn the plugin options reset the configuration and then reintroduce your changes.");
        }
    }

    /**
     *
     * http://www.asjava.com/core-java/java-md5-example/
     *
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
