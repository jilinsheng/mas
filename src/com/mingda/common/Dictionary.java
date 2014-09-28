package com.mingda.common;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mingda.dto.DictDTO;

@SuppressWarnings("unchecked")
public class Dictionary {

	private final static String filepath = "com/mingda/common/dictionary.xml";
	private static Dictionary instance;
	private Document dictionary;

	public Dictionary() {
		ReadXML readXML = new ReadXML(filepath);
		dictionary = readXML.readXml();
	}

	public static Dictionary getInstance() {
		if (null == instance) {
			instance = new Dictionary();
		}
		return instance;
	}

	public List<DictDTO> findDictBySort(Integer dictsortId) {

		List<DictDTO> list = new ArrayList<DictDTO>();
		List<Node> rs = dictionary.selectNodes("//DV[@dsid='" + dictsortId
				+ "']");

		for (Node n : rs) {
			DictDTO dict = new DictDTO();
			Element e = (Element) n;
			String dsid = e.attributeValue("dsid");
			String dvid = e.attributeValue("dvid");
			String item = e.getText();
			dict.setDictionaryId(new Integer(dvid));
			dict.setDictsortId(new Integer(dsid));
			dict.setItem(item);
			list.add(dict);
		}

		return list;
	}

	public String findDictvalue(Object dictionaryId) {

		if (null == dictionaryId || "".equals(dictionaryId.toString())) {
			return null;
		} else {
			Element e = (Element) dictionary.selectSingleNode("//DV[@dvid='"
					+ dictionaryId + "']");
			if (null == e) {
				return null;
			} else {
				return e.getText();
			}
		}
	}

	public Document getDictionary() {
		return dictionary;
	}

	public void setDictionary(Document dictionary) {
		this.dictionary = dictionary;
	}
}
