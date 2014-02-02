package net.sourceforge.jgeocoder.us;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpecialData{
    private static final ThreadLocal<Map<String, List<String>>> cityMap =
            new ThreadLocal<Map<String, List<String>>>() {
                @Override public Map<String, List<String>> initialValue() {
                    Map<String, List<String>> C_MAP = new HashMap<String, List<String>>();
                    BufferedReader r = null;
                    try {
                        String resourcePath = "src/main/resources/exception_city.txt";
                        InputStream is = new FileInputStream(resourcePath);
                        r = new BufferedReader(new InputStreamReader(is));
                        String line = null;
                        Map<String, Set<String>> tmp = new HashMap<String, Set<String>>();
                        while ((line = r.readLine()) != null) {
                            String[] items = line.split("\\s*->\\s*");
                            String[] cities = items[1].split("[|]");
                            String state = items[0];
                            Set<String> set = tmp.get(state);
                            if (set == null) {
                                set = new HashSet<String>();
                                tmp.put(state, set);
                            }
                            for (String city : cities) {
                                set.add(city);
                            }
                        }
                        for (Map.Entry<String, Set<String>> e : tmp.entrySet()) {
                            String[] array = e.getValue().toArray(new String[] {});
                            Arrays.sort(array, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return Integer.valueOf(o2.length()).compareTo(
                                            o1.length());
                                }
                            });
                            C_MAP.put(e.getKey(), Arrays.asList(array));
                        }
                    } catch (Exception e) {
                        throw new Error("Unable to initalize exception_city", e);
                    } finally {
                        if (r != null) {
                            try {
                                r.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                    return C_MAP;
                }
            };

    public static Map<String, List<String>> getCityMap() {
        return cityMap.get();
    }
}