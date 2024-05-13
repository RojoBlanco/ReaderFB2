package ru.read.reader.fb2format.BodyBlock;

import ru.read.reader.fb2format.Image;

import java.util.*;

public class Body {
	private Image image;
	private Title title;
	private List<Epigraph> epigraphs;

	private List<Section> sections;
	public Body(){
		sections = new ArrayList<>();
	}
	public void addToSections(Section section){
		sections.add(section);
	}
}
