package nl.impress.app;

import com.google.inject.Singleton;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class FreeMarkerUtil extends Configuration {

    public FreeMarkerUtil() throws IOException {
        super(Configuration.VERSION_2_3_21);

        setDefaultEncoding("UTF-8");
        setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    }

    /**
     * Get the HTML from a template name,
     * without merging the template with actual data.
     * @param templateName The name of the template, including ftl extension.
     * @return HTML representation of the FreeMarker Template fill.
     * @throws IOException
     * @throws TemplateException
     */
    public String getTemplateHTML(String templateName)
            throws IOException, TemplateException {
        Map<String, Object> emptyDataModel = new HashMap<>();
        return this.getTemplateHTML(templateName, emptyDataModel);
    }

    /**
     * Get the HTML from a template name,
     * and merge it with the provided data model.
     * @param templateName The name of the template, including ftl extension.
     * @param dataModel A map containing the key value pairs needed to fill the page with data.
     * @return HTML representation of the FreeMarker Template fill.
     * @throws IOException
     * @throws TemplateException
     */
    public String getTemplateHTML(String templateName, Map<String, Object> dataModel)
            throws IOException, TemplateException {

        Template template = this.getTemplate(templateName);
        StringWriter out = new StringWriter();
        template.process(dataModel, out);
        return out.toString();
    }
}
