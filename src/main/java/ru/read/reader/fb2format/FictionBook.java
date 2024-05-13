package ru.read.reader.fb2format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.read.reader.fb2format.DescriptionBlock.Description;
import ru.read.reader.fb2format.BodyBlock.Body;

public class FictionBook implements Comparable<FictionBook> {
	private String path;
	private Description desc;
	private final List<Body> listBody;
	private final Map<String, String> binary;
	private int index;

	public FictionBook() {
		desc = new Description();
		this.binary = new HashMap<>();
		this.listBody = new ArrayList<>();
	}

	public void setDesc(Description desc) {
		this.desc = desc;
	}

	public Description getDesc() {
		return desc;
	}

	public Map<String, String> getListBinary() {
		return binary;
	}

	public List<Body> getListBody() {
		return listBody;
	}

	public void setBinary(String id, String path) {
		binary.put(id, path);
	}

	public void addBody(Body body) {
		listBody.add(body);
	}

	public Description getDescription() {
		return desc;
	}

	public String getCoverPath() {
		return binary.getOrDefault("cover.jpg", "error");
	}

	public String getCoverName() {
		if (binary.containsKey("cover.jpg")) {
			return "cover.jpg";
		} else return "error";
	}

	public String getName() {
		return desc.getTitleInfo().getBookTitle();
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(FictionBook otherBook) {
		return Integer.compare(this.index, otherBook.index);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FictionBook obj1) {
			return (this.getDescription().getTitleInfo().getBookTitle().equals(obj1.getDescription().getTitleInfo().getBookTitle()) && this.getDescription().getTitleInfo().getAuthors().stream().allMatch(obj1.getDescription().getTitleInfo().getAuthors()::contains));
		}
		return false;
	}

	public void clearBody() {
		listBody.clear();
	}
}
