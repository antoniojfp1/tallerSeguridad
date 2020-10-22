package com.taller.dto;

import org.springframework.stereotype.Component;

@Component
public class File {
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
