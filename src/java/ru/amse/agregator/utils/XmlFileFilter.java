package ru.amse.agregator.utils;

import java.io.File;
import java.io.FilenameFilter;

public class XmlFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0,String name) {
		if(name.lastIndexOf(".xml") != -1) {
			System.out.println(name);
			return true;	
        } else {
			return false;
        }
	}
}
