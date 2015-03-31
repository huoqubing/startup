package com.sql.project.startup.service;

import java.io.File;

public interface FileService {
	public String[] upload(File file, String keyPath, String type, boolean compress);
}
