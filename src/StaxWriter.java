import java.io.FileOutputStream;

import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxWriter {
  private String configFile;
private DefaultTableModel tableCop;
  private int cnt;
  public void setFile(String configFile) {
    this.configFile = configFile;
  }


  StaxWriter(DefaultTableModel mod, int count)
  {
    this.tableCop = mod;
    this.cnt = count;
  }

  public void saveConfig() throws Exception {
    // create an XMLOutputFactory
    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    // create XMLEventWriter
    XMLEventWriter eventWriter = outputFactory
        .createXMLEventWriter(new FileOutputStream(configFile));
    // create an EventFactory
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    // create and write Start Tag
    StartDocument startDocument = eventFactory.createStartDocument();
    eventWriter.add(startDocument);

    // create config open tag
    StartElement configStartElement = eventFactory.createStartElement("",
        "", "competitor");


    for( int c=1;c<cnt;c++) {
      eventWriter.add(configStartElement);
      eventWriter.add(end);
      // Write the different nodes
      createNode(eventWriter, "Name", (String)tableCop.getValueAt(c, 1));
      createNode(eventWriter, "Surname", (String)tableCop.getValueAt(c, 2));
      createNode(eventWriter, "Weight", (String)tableCop.getValueAt(c, 3));
      createNode(eventWriter, "Age", (String)tableCop.getValueAt(c, 4));

      eventWriter.add(eventFactory.createEndElement("", "", "competitor"));
      eventWriter.add(end);
    }


    eventWriter.add(eventFactory.createEndDocument());
    eventWriter.close();
  }

  private void createNode(XMLEventWriter eventWriter, String name,
      String value) throws XMLStreamException {

    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // create Start node
    StartElement sElement = eventFactory.createStartElement("", "", name);
    eventWriter.add(tab);
    eventWriter.add(sElement);
    // create Content
    Characters characters = eventFactory.createCharacters(value);
    eventWriter.add(characters);
    // create End node
    EndElement eElement = eventFactory.createEndElement("", "", name);
    eventWriter.add(eElement);
    eventWriter.add(end);

  }

} 