package pl.edu.pw.elka.pszt.inteligraph.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import pl.edu.pw.elka.pszt.inteligraph.model.InputGraph;

public class GraphParser 
{
	static final String VERTEX = "vertex";
	static final String EDEG = "edeg";
	static final String NAME = "name";
	static final String BEGIN = "begin";
	static final String END = "end";
	private String EdegName;
	private Integer EdegBegin, EdegEnd;
	
	/**
	 * 
	 * 
	 */
	public InputGraph readConfig(String configFile) {
		
		InputGraph NewGraph = new InputGraph();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);


			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();

					if (startElement.getName().getLocalPart() == (VERTEX)) {

						event = eventReader.nextEvent();
						NewGraph.addNewVertex(Integer.parseInt(event.asCharacters().getData()));
					}
					if (startElement.getName().getLocalPart() == (EDEG)) {
						
					}
	
						if (event.isStartElement()) {
							if (event.asStartElement().getName().getLocalPart().equals(NAME)) 
							{
								event = eventReader.nextEvent();
								EdegName = event.asCharacters().getData();
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals(BEGIN)) 
							{
								event = eventReader.nextEvent();
								EdegBegin = Integer.parseInt(event.asCharacters().getData());
								continue;
							}
							if (event.asStartElement().getName().getLocalPart().equals(END)) 
							{
								event = eventReader.nextEvent();
								EdegEnd = Integer.parseInt(event.asCharacters().getData());
								continue;
							}
						}
				}
						if (event.isEndElement()) {
							EndElement endElement = event.asEndElement();
							if (endElement.getName().getLocalPart() == (EDEG)) 
							{
								NewGraph.addNewEdge(EdegName, EdegBegin, EdegEnd);
							}
						}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return NewGraph;
	}
	
	


}