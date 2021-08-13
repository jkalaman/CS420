package sample;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;



public class RSSFeedParser {

    public Feed readFeed (String url) {
        Document doc = stringToDom(cleanUpURL(url));
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");

        String description = "";
        String title = "";
        String link = "";
        String media = "";

        Feed feed = new Feed(title, link, description, media);
        int id = 0;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            FeedMessage message = new FeedMessage();

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                message.setTitle(eElement.getElementsByTagName("headline").item(0).getTextContent());
                message.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                message.setLink(eElement.getElementsByTagName("link").item(0).getTextContent());
                message.setMedia(eElement.getElementsByTagName("media:content").item(0).getTextContent());
                message.setId(temp);
                feed.getMessages().add(message);
            }
        }
        feed.setLength(nList.getLength());
        return feed;
    }

    private static String cleanUpURL(String url) {
        try {
            URL rssURL = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));
            String sourceCode = "";
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("<description><p>")){
                    line = line.replace("<description><p>", "<description>");
                }
                if (line.contains("</media:content>"))
                    line = "skipper";
                if (line.contains("<title>")) {
                    line = line.replace("<title>", "<headline>");
                    line = line.replace("</title>", "</headline>");
                }
                if (line.contains("url=")) {
                    int firstPos = line.indexOf("url=");
                    String temp = line.substring(firstPos);
                    temp = temp.replace("url=","");
                    // System.out.println("Temp is: " + temp);
                    line = "<media:content>" + temp + "</media:content>";

                }
                if(line.contains("<headline>"))
                    sourceCode += "\n" + line + "\n";
                else
                    sourceCode += line +"\n";
            }
            in.close();
            return sourceCode;
        }catch (MalformedURLException ue) {
            System.out.println("Malformed URL");
        }catch (IOException ioe) {
            System.out.println("Something wrong with the file");
        }
        return null;
    }



    private static Document stringToDom(String xmlSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlSource)));
        } catch (SAXException sae) {
            sae.printStackTrace();
        } catch (ParserConfigurationException pce){
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    } //end method string to Document


}