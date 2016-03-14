package com.cts.homesurveyor.util;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.cts.homesurveyor.adapter.gw.GWClaimAdapter;

/**
 * *
 * Handles the Velocity Template mappings
 *
 * @author Ayyanar Inbamohan (122685)
 *
 */
public class VelocityUtil {

    public static String getCalEngineRequestXML(String filePath,
            HashMap<String, String> values) {
        String resultXML = "";
        try {

            VelocityEngine ve = new VelocityEngine();
            URL url = GWClaimAdapter.class.getResource("testfiles/");

            File file = new File(url.getFile());
            ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
            ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                    file.getAbsolutePath());
            ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
            ve.init();
            VelocityContext context = new VelocityContext();

            for (Map.Entry<String, String> entry : values.entrySet()) {
                // System.out.println("Key = " + entry.getKey() + ", Value = " +
                // entry.getValue());
                context.put(entry.getKey(), entry.getValue());
            }

            final String templatePath = filePath;
            Template template = ve.getTemplate(templatePath, "UTF-8");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            resultXML = writer.toString();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("The Calcengine Template XML is " + resultXML);
        return resultXML;
    }
}
