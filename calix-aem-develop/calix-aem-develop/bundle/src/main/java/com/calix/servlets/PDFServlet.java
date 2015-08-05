package com.calix.servlets;

import com.calix.service.RestInvoker;
import com.calix.utils.JcrUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;


//PDFBOX
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDStream;

@SlingServlet(paths = "/bin/calixservice/pdfservlet", methods = {"GET", "POST"}, metatype = true)
public class PDFServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

    private String title;
    private String description;
    private String image;
    private String content;

    private final Logger log = LoggerFactory.getLogger(PDFServlet.class);

    @Reference
    private SlingRepository repository;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    RestInvoker invoker;

    public void bindRepository(SlingRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        try {

            String hostURL = null;
            if(request.getServerPort()!= 4502 && request.getServerPort()!= 4503 )
            {
                hostURL =  "http://" +  request.getServerName();
            }else{
                hostURL = "http://" + request.getServerName() + ":" + request.getServerPort() ;
            }

            log.info("SERVER_HOST " + hostURL );

            title = request.getParameter("title");
            description = request.getParameter("description");
            image = request.getParameter("image");
            content = request.getParameter("content");


            // Create the PDFBOx Object
            // Create a new empty document
            PDDocument document = new PDDocument();
            // Create a document and add a page to it
            PDPage page = new PDPage();
            document.addPage( page );

            float leading = 1.5f * 14;

            PDRectangle mediabox = page.findMediaBox();
            float margin = 72;
            float width = mediabox.getWidth() - 2*margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;

            // Create a new font object selecting one of the PDF base fonts
            PDFont fontPlain = PDType1Font.HELVETICA;
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // add title
            contentStream.beginText();
            contentStream.setFont(fontBold, 18);
            contentStream.moveTextPositionByAmount(70, 700);
            contentStream.drawString(title);
            contentStream.endText();

            // add description
            List<String> lines = new ArrayList<String>();
            lines = writeTextToPDF (description, 14, fontPlain,width );

            contentStream.beginText();
            contentStream.setFont(fontPlain, 14);
            contentStream.moveTextPositionByAmount(70, 680);
            for (String line: lines)
            {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -leading);
            }
            contentStream.endText();

            // add an image
            // check the path to image if have %20 in path of image, replace it to " "
            boolean hasWhiteSpaceInPath =  image.contains("%20");
            if(hasWhiteSpaceInPath)
                image = image.replaceAll("%20"," ");

            Node nodeImage = JcrUtils.getNode(request, image);
            InputStream in = nodeImage.getNode("jcr:content/renditions/original/jcr:content").getProperty("jcr:data").getStream();
            try {

                BufferedImage awtImage = ImageIO.read(in);
                PDXObjectImage ximage = new PDPixelMap(document, awtImage);
                float scale = 0.8f; // alter this value to set the image size
                contentStream.drawXObject(ximage, 70, 410, ximage.getWidth()*scale + 50, ximage.getHeight()*scale);
            } catch (FileNotFoundException fnfex) {
                log.error("Exception in sample service: ");
            }
            // add content
            List<String> linesContent = new ArrayList<String>();
            content = content.replaceAll("                " , " ");
            linesContent = writeTextToPDF (content, 10, fontPlain,width );

            contentStream.beginText();
            contentStream.setFont(fontPlain, 10);
            contentStream.moveTextPositionByAmount(65, 380);
            for (String line: linesContent)
            {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -leading);
            }
            contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();

            //Save the PDF into the AEM DAM
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            document.save(out);

            byte[] myBytes = out.toByteArray();
            InputStream is = new ByteArrayInputStream(myBytes) ;
            String damLocation = writeToDam(is,"pressNewRelease");

            log.info("---------------------FINISH PDF------------------------------------------");
            document.close();

        } catch (Exception e) {
            log.error("Exception in pdf service: ", e);
        }
    }

    //Save the uploaded file into the Adobe CQ DAM
    private String writeToDam(InputStream is, String fileName)
    {
        try
        {
            //Invoke the adaptTo method to create a Session used to create a QueryManager
            ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
            //Use AssetManager to place the file into the AEM DAM
            com.day.cq.dam.api.AssetManager assetMgr = resourceResolver.adaptTo(com.day.cq.dam.api.AssetManager.class);
            String newFile = "/content/dam/calix/pdf/"+fileName ;
            assetMgr.createAsset(newFile, is, "application/pdf", true);
            return newFile;
        }
        catch(Exception e)
        {

            log.error("Exception in pdf service uploaded file into the Adobe CQ DAM" );
        }
        return null;
    }

    private List<String> writeTextToPDF (String text, float fontSize, PDFont pdfFont, float width){
        try {

            List<String> lines = new ArrayList<String>();
            int lastSpace = -1;
            while (text.length() > 0) {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(text);
                    text = "";
                } else {
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                    if (size > width) {
                        if (lastSpace < 0) // So we have a word longer than the line... draw it anyways
                            lastSpace = spaceIndex;
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }
            return lines;
        }catch (IOException e) {
            log.error("Exception in writeTextToPDF ");
            return null;
        }

    }


}

